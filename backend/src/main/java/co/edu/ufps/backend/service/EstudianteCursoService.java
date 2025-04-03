package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.EstudianteCurso;
import co.edu.ufps.backend.repository.EstudianteCursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstudianteCursoService {
    @Autowired
    private final EstudianteCursoRepository estudianteCursoRepository;

    // Obtener todos los registros de EstudianteCurso
    public List<EstudianteCurso> getAll() {
        return estudianteCursoRepository.findAll();
    }

    // Obtener un registro por su ID
    public Optional<EstudianteCurso> getById(Long id) {
        return estudianteCursoRepository.findById(id);
    }

    // Crear un nuevo registro de EstudianteCurso
    public EstudianteCurso create(EstudianteCurso estudianteCurso) {
        return estudianteCursoRepository.save(estudianteCurso);
    }

    // Actualizar un registro existente por ID
    public EstudianteCurso update(Long id, EstudianteCurso detalles) {
        return estudianteCursoRepository.findById(id).map(estudianteCurso -> {
            estudianteCurso.setEstado(detalles.getEstado());
            estudianteCurso.setHabilitacion(detalles.getHabilitacion());
            estudianteCurso.setEstudiante(detalles.getEstudiante());
            estudianteCurso.setCurso(detalles.getCurso());
            return estudianteCursoRepository.save(estudianteCurso);
        }).orElseThrow(() -> new RuntimeException("EstudianteCurso no encontrado"));
    }

    // Eliminar un registro por ID
    public void delete(Long id) {
        estudianteCursoRepository.deleteById(id);
    }

    // Implementar métodos personalizados
    public void agregarAsistencia(Long id) {
        estudianteCursoRepository.findById(id).ifPresent(EstudianteCurso::agregarAsistencia);
    }

    public void calcularDefinitiva(Long id) {
        estudianteCursoRepository.findById(id).ifPresent(EstudianteCurso::calcularDefinitiva);
    }

    public void comprobarRehabilitacion(Long id) {
        estudianteCursoRepository.findById(id).ifPresent(EstudianteCurso::comprobarRehabilitacion);
    }

    public void cancelar(Long id) {
        estudianteCursoRepository.findById(id).ifPresent(EstudianteCurso::cancelar);
    }

    public void matricularCurso(Long id) {
        estudianteCursoRepository.findById(id).ifPresent(EstudianteCurso::matricularCurso);
    }
}