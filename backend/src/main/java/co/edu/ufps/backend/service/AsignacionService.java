package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.*;
import co.edu.ufps.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsignacionService {

    @Autowired
    private final AsignacionRepository asignacionRepository;
    private CalificacionService calificacionService;
    private final AsistenciaService asistenciaService;
    private final DocenteService docenteService;
    private final CursoService cursoService;
    private final HorarioCursoService horarioCursoService;
    private final EstudianteCursoRepository estudianteCursoRepository;
    private final AsistenciaRepository asistenciaRepository;

    public List<Curso> getCursosByDocente(Long docenteId) {
        List<Asignacion> asignaciones = asignacionRepository.findByDocenteId(docenteId);
        return asignaciones.stream()
                .map(Asignacion::getCurso)
                .toList();
    }



    /**
     * Obtener todas las asignaciones
     * @return lista de asignaciones
     */
    public List<Asignacion> getAllAsignaciones() {
        return asignacionRepository.findAll();
    }

    /**
     * Obtener una asignación por su id
     * @param id ID compuesto de la asignación
     * @return Asignación encontrada o un Optional vacío si no existe
     */
    public Optional<Asignacion> getAsignacionById(Long id) {
        return asignacionRepository.findById(id);
    }

    /**
     * Crear una nueva asignación
     * @param asignacion Objeto de tipo Asignacion
     * @return Asignación creada
     */
    public Asignacion createAsignacion(Asignacion asignacion) {
        return asignacionRepository.save(asignacion);
    }

    /**
     * Actualizar una asignación existente
     * @param id ID compuesto de la asignación a actualizar
     * @param asignacionDetails Detalles nuevos de la asignación
     * @return Asignación actualizada
     */
    public Asignacion updateAsignacion(Long id, Asignacion asignacionDetails) {
        return asignacionRepository.findById(id).map(asignacion -> {
            asignacion.setDocente(asignacionDetails.getDocente());
            asignacion.setCurso(asignacionDetails.getCurso());
            return asignacionRepository.save(asignacion);
        }).orElseThrow(() -> new RuntimeException("Asignación no encontrada"));
    }

    /**
     * Eliminar una asignación por su id
     * @param id ID compuesto de la asignación a eliminar
     */
    public void deleteAsignacion(Long id) {
        asignacionRepository.deleteById(id);
    }


    public Asignacion asignarDocente(Long docenteId, Long cursoId) {
        // 1. Obtener el docente desde su servicio
        Docente docente = docenteService.getDocenteByCodigo(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        // 2. Obtener el curso desde su servicio
        Curso curso = cursoService.getCursoById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // 3. Validar si ya hay una asignación existente (puedes crear un método en AsignacionService para esto si lo deseas)
        Optional<Asignacion> asignacionExistente = asignacionRepository.findByCursoId(cursoId);
        if (asignacionExistente.isPresent()) {
            throw new RuntimeException("Ya existe un docente asignado a este curso");
        }

        // Validar conflictos de horario
        validarHorariosDocente(docenteId, cursoId);

        // 5. Si pasa todas las validaciones, crear la asignación
        Asignacion asignacion = new Asignacion();
        asignacion.setDocente(docente);
        asignacion.setCurso(curso);

        return createAsignacion(asignacion);
    }

    private void validarHorariosDocente(Long docenteId, Long cursoId) {
        try {
            Curso cursoNuevo = cursoService.getCursoById(cursoId)
                    .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

            List<HorarioCurso> horariosNuevoCurso = horarioCursoService.getAllHorariosByCurso(cursoId);
            if (horariosNuevoCurso.isEmpty()) {
                throw new RuntimeException("El curso no tiene horarios definidos.");
            }

            // Obtener todos los cursos asignados previamente al docente
            List<Curso> cursosDelDocente = getCursosByDocente(docenteId);

            for (Curso cursoActual : cursosDelDocente) {
                List<HorarioCurso> horariosCursoActual = horarioCursoService.getAllHorariosByCurso(cursoActual.getId());

                for (HorarioCurso horarioActual : horariosCursoActual) {
                    for (HorarioCurso horarioNuevo : horariosNuevoCurso) {
                        if (horarioCursoService.hayConflictoHorario(horarioActual, horarioNuevo)) {
                            throw new RuntimeException(
                                    "Conflicto de horario con el curso '" + cursoActual.getNombre() +
                                            "'. Día: " + horarioNuevo.getDia() +
                                            ", Hora: " + horarioNuevo.getHoraInicio() + " - " + horarioNuevo.getHoraFin()
                            );
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al validar horarios del docente: " + e.getMessage(), e);
        }
    }




    public Asistencia registrarAsistencia(Long estudianteCursoId, Asistencia asistenciaInput) {
        return asistenciaService.registrarAsistencia(estudianteCursoId, asistenciaInput);
    }

    public Calificacion registrarCalificacionDesdeAsignacion(Long docenteId, Long cursoId, Long estudianteCursoId, Calificacion calificacionInput) {
        // Validar que el docente está asignado al curso
        Optional<Asignacion> asignacion = asignacionRepository.findByDocenteIdAndCursoId(docenteId, cursoId);
        if (asignacion.isEmpty()) {
            throw new RuntimeException("El docente no está asignado a este curso.");
        }

        // Verificar que el estudianteCurso pertenece al curso dado
        EstudianteCurso estudianteCurso = estudianteCursoRepository.findById(estudianteCursoId)
                .orElseThrow(() -> new RuntimeException("EstudianteCurso no encontrado"));
        if (!estudianteCurso.getCurso().getId().equals(cursoId)) {
            throw new RuntimeException("Este estudiante no está en el curso especificado.");
        }

        // Verificar si ya existe una calificación de ese tipo
        boolean yaExiste = calificacionService.tipoYaExisteParaEstudianteCurso(estudianteCursoId, calificacionInput.getTipo());
        if (yaExiste) {
            throw new RuntimeException("Ya existe una calificación de tipo " + calificacionInput.getTipo());
        }

        // Asociar y guardar la calificación
        calificacionInput.setEstudianteCurso(estudianteCurso);
        return calificacionService.createCalificacion(calificacionInput);
    }


    public List<EstudianteCurso> getEstudiantesPorCursoYDocente(Long docenteId, Long cursoId) {
        // Verificar que el curso está asignado a ese docente
        Optional<Asignacion> asignacion = asignacionRepository.findByDocenteIdAndCursoId(docenteId, cursoId);
        if (asignacion.isEmpty()) {
            throw new RuntimeException("El curso no está asignado a este docente");
        }

        // Si la asignación existe, obtener los estudiantes inscritos en el curso
        return estudianteCursoRepository.findByCursoId(cursoId);
    }



    public Asistencia registrarAsistenciaDeProfesor(Long docenteId, Long cursoId, Long estudianteCursoId, Asistencia asistenciaInput) {
        // Verifica que el curso esté asignado al docente
        Optional<Asignacion> asignacion = asignacionRepository.findByDocenteIdAndCursoId(docenteId, cursoId);
        if (asignacion.isEmpty()) {
            throw new RuntimeException("Este curso no está asignado al docente.");
        }

        // Verifica que el estudiante esté inscrito en ese curso
        EstudianteCurso estudianteCurso = estudianteCursoRepository.findById(estudianteCursoId)
                .orElseThrow(() -> new RuntimeException("EstudianteCurso no encontrado"));

        if (!estudianteCurso.getCurso().getId().equals(cursoId)) {
            throw new RuntimeException("El estudiante no pertenece a este curso.");
        }

        // Crear la asistencia
        Asistencia asistencia = new Asistencia();
        asistencia.setEstudianteCurso(estudianteCurso);
        asistencia.setFecha(asistenciaInput.getFecha() != null ? asistenciaInput.getFecha() : new Date());
        asistencia.setEstado(asistenciaInput.getEstado());
        asistencia.setExcusa(asistenciaInput.getExcusa());

        return asistenciaRepository.save(asistencia);
    }




}