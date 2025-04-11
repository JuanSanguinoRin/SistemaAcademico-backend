package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.*;
import co.edu.ufps.backend.repository.AsistenciaRepository;
import co.edu.ufps.backend.repository.EstudianteCursoRepository;
import co.edu.ufps.backend.repository.CalificacionRepository;
import co.edu.ufps.backend.repository.EstudianteRepository;
import co.edu.ufps.backend.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstudianteCursoService {
    private final EstudianteCursoRepository estudianteCursoRepository;
    private final AsistenciaRepository asistenciaRepository;
    private final CalificacionRepository calificacionRepository;
    private final EstudianteRepository estudianteRepository;
    private final CursoRepository  cursoRepository;

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

        return asistenciaRepository.save(asistencia);
    }

    public Float calcularDefinitiva(Long estudianteCursoId) {
        List<Calificacion> calificaciones = calificacionRepository.findByEstudianteCursoId(estudianteCursoId);

        if (calificaciones.isEmpty()) {
            throw new RuntimeException("No hay calificaciones para este estudiante en este curso.");
        }

        float suma = 0f;
        for (Calificacion c : calificaciones) {
            suma += c.getNota();
        }

        return suma / calificaciones.size(); // Promedio simple
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
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Validar si ya está matriculado
        Optional<EstudianteCurso> yaMatriculado = estudianteCursoRepository.findByCursoIdAndEstudianteCodigoEstudiante(cursoId, estudianteId);
        if (yaMatriculado.isPresent()) {
            throw new RuntimeException("El estudiante ya está matriculado en este curso.");
        }

        EstudianteCurso inscripcion = new EstudianteCurso();
        inscripcion.setEstudiante(estudiante);
        inscripcion.setCurso(curso);
        inscripcion.setEstado("Cursando");
        inscripcion.setHabilitacion(false); // Por defecto no es habilitación

        return estudianteCursoRepository.save(inscripcion);
    }

}