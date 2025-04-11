package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.*;
import co.edu.ufps.backend.repository.EstudianteCursoRepository;
import co.edu.ufps.backend.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstudianteCursoService {
    private final EstudianteCursoRepository estudianteCursoRepository;
    private final CursoRepository  cursoRepository;

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

    //registrarAsistencia  VA EN ASISTENCIA y la invoca asignacion
    /**/

    public EstudianteCurso getInscripcion(Long cursoId, Long estudianteId) {
        return estudianteCursoRepository
                .findByCursoIdAndEstudianteCodigoEstudiante(cursoId, estudianteId)
                .orElseThrow(() -> new RuntimeException("El estudiante no está inscrito en este curso"));
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





}