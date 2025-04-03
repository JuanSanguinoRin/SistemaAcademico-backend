package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Aula;
import co.edu.ufps.backend.repository.AulaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AulaService {
    @Autowired
    private final AulaRepository aulaRepository;

    public List<Aula> getAllAulas() {
        return aulaRepository.findAll();
    }

    public Optional<Aula> getAulaById(Long id) {
        return aulaRepository.findById(id);
    }

    public Aula createAula(Aula aula) {
        return aulaRepository.save(aula);
    }

    public Aula updateAula(Long id, Aula aulaDetails) {
        return aulaRepository.findById(id).map(aula -> {
            aula.setCapacidad(aulaDetails.getCapacidad());
            aula.setUbicacion(aulaDetails.getUbicacion());
            aula.setDimensiones(aulaDetails.getDimensiones());
            aula.setTipo(aulaDetails.getTipo());
            aula.setEstado(aulaDetails.getEstado());
            aula.setEsExamen(aulaDetails.getEsExamen());
            return aulaRepository.save(aula);
        }).orElseThrow(() -> new RuntimeException("Aula not found"));
    }

    public void deleteAula(Long id) {
        aulaRepository.deleteById(id);
    }
}

