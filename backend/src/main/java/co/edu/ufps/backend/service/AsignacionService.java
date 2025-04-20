package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.*;
import co.edu.ufps.backend.repository.AsignacionRepository;
import co.edu.ufps.backend.repository.CursoRepository;
import co.edu.ufps.backend.repository.DocenteRepository;
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

    public List<Curso> getCursosByDocente(Long docenteId) {
        return asignacionRepository.findByDocenteId(docenteId);
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

}