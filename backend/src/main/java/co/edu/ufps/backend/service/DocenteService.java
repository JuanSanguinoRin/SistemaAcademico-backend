package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Docente;
import co.edu.ufps.backend.repository.DocenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocenteService {
  
    @Autowired
    private final DocenteRepository docenteRepository;
    public List<Docente> getAllDocentes() {
        return docenteRepository.findAll();
    }

    // Obtener un docente por su código
    public Optional<Docente> getDocenteByCodigo(Long codigoDocente) {
        return docenteRepository.findById(codigoDocente);
    }

    // Crear un nuevo docente
    public Docente createDocente(Docente docente) {
        return docenteRepository.save(docente);
    }

    // Actualizar un docente existente
    public Docente updateDocente(Long codigoDocente, Docente docenteDetails) {
        return docenteRepository.findById(codigoDocente).map(docente -> {
            docente.setExperiencia(docenteDetails.getExperiencia());
            docente.setPrograma(docenteDetails.getPrograma());
            docente.setPersona(docenteDetails.getPersona());
            return docenteRepository.save(docente);
        }).orElseThrow(() -> new RuntimeException("Docente no encontrado"));
    }

    // Eliminar un docente por su código
    public void deleteDocente(Long codigoDocente) {
        docenteRepository.deleteById(codigoDocente);
    }
}

