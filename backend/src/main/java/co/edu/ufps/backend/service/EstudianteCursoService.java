package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.*;
import co.edu.ufps.backend.repository.AsistenciaRepository;
import co.edu.ufps.backend.repository.EstudianteCursoRepository;
import co.edu.ufps.backend.repository.CalificacionRepository;
import co.edu.ufps.backend.repository.EstudianteRepository;
import co.edu.ufps.backend.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstudianteCursoService {
    private final EstudianteCursoRepository estudianteCursoRepository;
    private final AsistenciaService asistenciaService;
    private final CalificacionService calificacionService;
    private final EstudianteService estudianteService;
    private final CursoService cursoService;
    private final AsignaturaPrerrequisitoService asignaturaPrerrequisitoService;
    private final AsignaturaService asignaturaService;
    private final HorarioCursoService horarioCursoService;

    public List<EstudianteCurso> getAllEstudianteCursos() {
        return estudianteCursoRepository.findAll();
    }

    public Optional<EstudianteCurso> getEstudianteCursoById(Long id) {
        return estudianteCursoRepository.findById(id);
    }
    //es lo mismo que getEstudianteCursoById pero sirve para suponer que siempre se encuentr el id, se usara solo en el backend
    public EstudianteCurso getById(Long id) {
        return estudianteCursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación Estudiante-Curso no encontrada"));
    }


    public List<EstudianteCurso> getEstudianteCursosByEstudiante(Long estudianteId) {
        return estudianteCursoRepository.findByEstudianteCodigoEstudiante(estudianteId);
    }

    public List<EstudianteCurso> getEstudianteCursosByCurso(Long cursoId) {
        return estudianteCursoRepository.findByCursoId(cursoId);
    }

    public Optional<EstudianteCurso> getEstudianteCursoByEstudianteAndCurso(Long estudianteId, Long cursoId) {
        return estudianteCursoRepository.findByEstudianteCodigoEstudianteAndCursoId(estudianteId, cursoId);
    }

    public List<EstudianteCurso> getEstudianteCursosByEstado(String estado) {
        return estudianteCursoRepository.findByEstado(estado);
    }

    public EstudianteCurso createEstudianteCurso(EstudianteCurso estudianteCurso) {
        return estudianteCursoRepository.save(estudianteCurso);
    }

    public EstudianteCurso updateEstudianteCurso(Long id, EstudianteCurso estudianteCursoDetails) {
        return estudianteCursoRepository.findById(id).map(estudianteCurso -> {
            estudianteCurso.setCurso(estudianteCursoDetails.getCurso());
            estudianteCurso.setEstudiante(estudianteCursoDetails.getEstudiante());
            estudianteCurso.setEstado(estudianteCursoDetails.getEstado());
            estudianteCurso.setHabilitacion(estudianteCursoDetails.getHabilitacion());
            return estudianteCursoRepository.save(estudianteCurso);
        }).orElseThrow(() -> new RuntimeException("EstudianteCurso not found"));
    }

    public void deleteEstudianteCurso(Long id) {
        estudianteCursoRepository.deleteById(id);
    }
    public EstudianteCurso getInscripcion(Long cursoId, Long estudianteId) {
        return estudianteCursoRepository
                .findByCursoIdAndEstudianteCodigoEstudiante(cursoId, estudianteId)
                .orElseThrow(() -> new RuntimeException("El estudiante no está inscrito en este curso"));
    }

    public Float calcularDefinitiva(Long estudianteCursoId) {

        Optional<EstudianteCurso> estudianteCurso = this.getEstudianteCursoById(estudianteCursoId);

        if (estudianteCurso.isEmpty())
        {

            throw new RuntimeException("Estudiante-Curso no encontrado.");

        }

        EstudianteCurso aux = estudianteCurso.get();
        List<Calificacion> calificaciones = calificacionService.getCalificacionesByEstudianteCurso(aux);

        if (calificaciones.isEmpty()) {
            throw new RuntimeException("No hay calificaciones para este estudiante en este curso.");
        }

        float suma = 0f;
        for (Calificacion c : calificaciones) {

            if(c.getTipo().equals("H")){

                return c.getNota();

            }else if(c.getTipo().equals("EX"))
            {

                suma += c.getNota() * 0.30f;

            }else{

                suma += c.getNota() * 0.2333f;

            }

        }

        return suma;
    }

    public Boolean comprobarRehabilitacion(Long estudianteCursoId) {
        EstudianteCurso ec = estudianteCursoRepository.findById(estudianteCursoId)
                .orElseThrow(() -> new RuntimeException("EstudianteCurso no encontrado"));

        return Boolean.TRUE.equals(ec.getHabilitacion());
    }

    public void cancelar(Long estudianteCursoId) {
        // Lógica para cancelar curso
        EstudianteCurso estudianteCurso = estudianteCursoRepository.findById(estudianteCursoId)
                .orElseThrow(() -> new RuntimeException("EstudianteCurso not found"));
        estudianteCurso.setEstado("cancelado");
        estudianteCursoRepository.save(estudianteCurso);
    }

    public EstudianteCurso matricularCurso(Long estudianteId, Long cursoId) {
        Estudiante estudiante = estudianteService.getEstudianteById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Curso curso = cursoService.getCursoById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Validar si ya está matriculado
        Optional<EstudianteCurso> yaMatriculado = estudianteCursoRepository.findByCursoIdAndEstudianteCodigoEstudiante(cursoId, estudianteId);
        if (yaMatriculado.isPresent()) {
            throw new RuntimeException("El estudiante ya está matriculado en este curso.");
        }

        List<EstudianteCurso> estudiantesMatriculados = this.getEstudianteCursosByCurso(cursoId);

        int cuposOcupados = estudiantesMatriculados.size();

        if (cuposOcupados >= curso.getCupoMaximo()) {
            throw new RuntimeException("No hay cupos disponibles en este curso.");
        }

        Optional<Asignatura> asignaturaOpt = cursoService.getAsignaturaByCurso(cursoId);
        if (!asignaturaOpt.isPresent()) {
            throw new RuntimeException("El curso no tiene una asignatura asociada.");
        }

        Asignatura asignatura = asignaturaOpt.get();

        // Validar prerrequisitos
        validarPrerrequisitos(estudianteId, asignatura);

        // Validar horarios
        validarHorarios(estudianteId, cursoId);

        EstudianteCurso inscripcion = new EstudianteCurso();
        inscripcion.setEstudiante(estudiante);
        inscripcion.setCurso(curso);
        inscripcion.setEstado("Cursando");
        inscripcion.setHabilitacion(false);

        return estudianteCursoRepository.save(inscripcion);
    }

    private void validarPrerrequisitos(Long estudianteId, Asignatura asignatura) {
        // Obtener prerrequisitos de la asignatura
        List<AsignaturaPrerrequisito> prerrequisitosAsignatura = asignaturaPrerrequisitoService.getAllPrerrequisitosByAsignaturaId(asignatura.getId());

        if (prerrequisitosAsignatura.isEmpty()) {
            return;
        }

        // Obtener cursos aprobados del estudiante
        List<EstudianteCurso> cursosAprobados = this.getCursosAprobadosByEstudiante(estudianteId);

        // Crear una lista de IDs de asignaturas aprobadas para facilitar la verificación
        List<Long> asignaturasAprobadasIds = cursosAprobados.stream()
                .map(ec -> ec.getCurso().getAsignatura().getId())
                .collect(Collectors.toList());

        // Verificar que todos los prerrequisitos estén aprobados
        List<Asignatura> prerrequisitosNoAprobados = new ArrayList<>();

        for (AsignaturaPrerrequisito prerreq : prerrequisitosAsignatura) {
            Long idPrerrequisito = prerreq.getPrerrequisito().getId();

            if (!asignaturasAprobadasIds.contains(idPrerrequisito)) {
                prerrequisitosNoAprobados.add(prerreq.getPrerrequisito());
            }
        }

        if (!prerrequisitosNoAprobados.isEmpty()) {
            StringBuilder mensaje = new StringBuilder("El estudiante no cumple con los siguientes prerrequisitos: ");
            prerrequisitosNoAprobados.forEach(a -> mensaje.append(a.getNombre()).append(", "));
            mensaje.delete(mensaje.length() - 2, mensaje.length());

            throw new RuntimeException(mensaje.toString());
        }
    }

    private void validarHorarios(Long estudianteId, Long cursoId) {

        Curso cursoAMatricular = cursoService.getCursoById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        List<HorarioCurso> horariosNuevoCurso = horarioCursoService.getAllHorariosByCurso(cursoId);

        if (horariosNuevoCurso.isEmpty()) {
            throw new RuntimeException("El curso no tiene horarios definidos.");
        }

        // Obtener cursos actuales del estudiante
        List<EstudianteCurso> cursosActuales = estudianteCursoRepository.findByEstudianteCodigoEstudianteAndEstado(
                estudianteId, "Cursando");

        // Para cada curso del estudiante, verificar si hay conflicto de horarios
        for (EstudianteCurso ec : cursosActuales) {
            List<HorarioCurso> horariosCursoActual = horarioCursoService.getAllHorariosByCurso(ec.getCurso().getId());

            for (HorarioCurso horarioActual : horariosCursoActual) {
                for (HorarioCurso horarioNuevo : horariosNuevoCurso) {
                    if (hayConflictoHorario(horarioActual, horarioNuevo)) {
                        throw new RuntimeException(
                                "Conflicto de horario con el curso " + ec.getCurso().getNombre() +
                                        ". Día: " + horarioNuevo.getDia() +
                                        ", Hora: " + horarioNuevo.getHoraInicio() + " - " + horarioNuevo.getHoraFin());
                    }
                }
            }
        }
    }

    private boolean hayConflictoHorario(HorarioCurso h1, HorarioCurso h2) {

        if (!h1.getDia().equals(h2.getDia())) {
            return false;
        }

        LocalTime inicio1 = h1.getHoraInicio();
        LocalTime fin1 = h1.getHoraFin();
        LocalTime inicio2 = h2.getHoraInicio();
        LocalTime fin2 = h2.getHoraFin();

        return (inicio1.isBefore(fin2) && inicio2.isBefore(fin1));
    }

    // Metodo para obtener cursos aprobados de un estudiante específico
    public List<EstudianteCurso> getCursosAprobadosByEstudiante(Long estudianteId) {
        return estudianteCursoRepository.findByEstudianteCodigoEstudianteAndEstado(estudianteId, "Aprobado");
    }
}