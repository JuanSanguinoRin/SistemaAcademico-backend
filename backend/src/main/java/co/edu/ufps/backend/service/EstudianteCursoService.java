package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.*;
import co.edu.ufps.backend.repository.EstudianteCursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstudianteCursoService {
    private final EstudianteCursoRepository estudianteCursoRepository;
    private final AsistenciaService asistenciaService;
    private final CalificacionService calificacionService;
    private final EstudianteService estudianteService;
    private final CursoService cursoService;
    private final AsignaturaPrerrequisitoService asignaturaPrerrequisitoService;

    public List<EstudianteCurso> getAllEstudianteCursos() {
        return estudianteCursoRepository.findAll();
    }

    public Optional<EstudianteCurso> getEstudianteCursoById(Long id) {
        return estudianteCursoRepository.findById(id);
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

    public Asistencia registrarAsistencia(Long estudianteCursoId, Asistencia asistenciaInput) {
        EstudianteCurso ec = estudianteCursoRepository.findById(estudianteCursoId)
                .orElseThrow(() -> new RuntimeException("Relación Estudiante-Curso no encontrada"));

        Asistencia asistencia = new Asistencia();
        asistencia.setEstudianteCurso(ec);
        asistencia.setFecha(asistenciaInput.getFecha());
        asistencia.setEstado(asistenciaInput.getEstado());
        asistencia.setExcusa(asistenciaInput.getExcusa());

        return asistenciaService.createAsistencia(asistencia);
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

        // 1. Validar prerrequisitos de la asignatura
        validarPrerrequisitos(estudianteId, curso.getAsignatura());

        // 2. Validar solapamiento de horarios
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


        // Obtener cursos aprobados del estudiante
        List<EstudianteCurso> cursosAprobados = this.getCursosAprobadosByEstudiante(estudianteId);

        // Verificar que todos los prerrequisitos estén aprobados
        for (AsignaturaPrerrequisito prerreq : prerrequisitos) {
            boolean aprobado = cursosAprobados.stream()
                    .anyMatch(ec -> ec.getCurso().getAsignatura().getCodigo().equals(prerreq.getPrerrequisito().getCodigo()));

            if (!aprobado) {
                throw new RuntimeException("Prerrequisito no aprobado: " + prerreq.getPrerrequisito().getNombre());
            }
        }
    }

    // Método para obtener cursos aprobados de un estudiante específico
    public List<EstudianteCurso> getCursosAprobadosByEstudiante(Long estudianteId) {
        return estudianteCursoRepository.findByEstudianteCodigoEstudianteAndEstado(estudianteId, "Aprobado");
    }
}