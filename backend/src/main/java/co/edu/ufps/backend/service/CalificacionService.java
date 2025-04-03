package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Calificacion;
import co.edu.ufps.backend.repository.CalificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalificacionService {
    @Autowired
    private final CalificacionRepository calificacionRepository;

    public List<Calificacion> getAllCalificaciones() {
        return calificacionRepository.findAll();
    }

    public Optional<Calificacion> getCalificacionById(Long id) {
        return calificacionRepository.findById(id);
    }

    public Calificacion createCalificacion(Calificacion calificacion) {
        return calificacionRepository.save(calificacion);
    }

    public Calificacion updateCalificacion(Long id, Calificacion calificacionDetails) {
        return calificacionRepository.findById(id).map(calificacion -> {
            calificacion.setNombre(calificacionDetails.getNombre());
            calificacion.setFecha(calificacionDetails.getFecha());
            calificacion.setTipo(calificacionDetails.getTipo());
            calificacion.setNota(calificacionDetails.getNota());
            calificacion.setEstudianteCurso(calificacionDetails.getEstudianteCurso());
            return calificacionRepository.save(calificacion);
        }).orElseThrow(() -> new RuntimeException("Calificacion not found"));
    }

    public void deleteCalificacion(Long id) {
        calificacionRepository.deleteById(id);
    }
}