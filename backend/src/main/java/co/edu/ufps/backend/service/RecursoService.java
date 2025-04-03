package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Recurso;
import co.edu.ufps.backend.repository.RecursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecursoService {
    @Autowired
    private final RecursoRepository recursoRepository;

    public List<Recurso> getAllRecursos() {
        return recursoRepository.findAll();
    }

    public Optional<Recurso> getRecursoById(Long id) {
        return recursoRepository.findById(id);
    }

    public Recurso createRecurso(Recurso recurso) {
        return recursoRepository.save(recurso);
    }

    public Recurso updateRecurso(Long id, Recurso recursoDetails) {
        return recursoRepository.findById(id).map(recurso -> {
            recurso.setNombre(recursoDetails.getNombre());
            recurso.setUbicacion(recursoDetails.getUbicacion());
            recurso.setEstadoMateria(recursoDetails.getEstadoMateria());
            return recursoRepository.save(recurso);
        }).orElseThrow(() -> new RuntimeException("Recurso not found"));
    }

    public void deleteRecurso(Long id) {
        recursoRepository.deleteById(id);
    }
}
