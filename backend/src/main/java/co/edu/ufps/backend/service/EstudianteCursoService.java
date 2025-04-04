package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.EstudianteCurso;
import co.edu.ufps.backend.repository.EstudianteCursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstudianteCursoService {
    private final EstudianteCursoRepository estudianteCursoRepository;

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

    public void agregarAsistencia(Long estudianteCursoId, String fecha, Boolean asistio) {
        // Lógica para agregar asistencia
    }

    public Float calcularDefinitiva(Long estudianteCursoId) {
        // Lógica para calcular nota definitiva
        return 0.0f; // Placeholder
    }

    public Boolean comprobarRehabilitacion(Long estudianteCursoId) {
        // Lógica para comprobar si el estudiante puede hacer habilitación
        return false; // Placeholder
    }

    public void cancelar(Long estudianteCursoId) {
        // Lógica para cancelar curso
        EstudianteCurso estudianteCurso = estudianteCursoRepository.findById(estudianteCursoId)
                .orElseThrow(() -> new RuntimeException("EstudianteCurso not found"));
        estudianteCurso.setEstado("cancelado");
        estudianteCursoRepository.save(estudianteCurso);
    }

    public void matricularCurso(Long estudianteId, Long cursoId) {
        // Lógica para matricular curso
    }
}