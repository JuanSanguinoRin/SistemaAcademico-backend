package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Semestre;
import co.edu.ufps.backend.repository.SemestreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SemestreService {

    @Autowired
    private final SemestreRepository semestreRepository;

    public List<Semestre> getAllSemestres() {
        return semestreRepository.findAll();
    }

    public Optional<Semestre> getSemestreById(Long id) {
        return semestreRepository.findById(id);
    }

    public Semestre createSemestre(Semestre semestre) {
        return semestreRepository.save(semestre);
    }

    public Semestre updateSemestre(Long id, Semestre semestreDetails) {
        return semestreRepository.findById(id).map(semestre -> {
            semestre.setFechaInicio(semestreDetails.getFechaInicio());
            semestre.setFechaFin(semestreDetails.getFechaFin());
            semestre.setAnio(semestreDetails.getAnio());
            semestre.setPeriodo(semestreDetails.getPeriodo());
            return semestreRepository.save(semestre);
        }).orElseThrow(() -> new RuntimeException("Semestre not found"));
    }

    public void deleteSemestre(Long id) {
        semestreRepository.deleteById(id);
    }



}