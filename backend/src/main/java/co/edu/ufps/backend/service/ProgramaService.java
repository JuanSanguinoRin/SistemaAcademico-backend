package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Programa;
import co.edu.ufps.backend.repository.ProgramaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgramaService {

    @Autowired
    private final ProgramaRepository programaRepository;

    // Obtener todos los programas
    public List<Programa> getAllProgramas() {
        return programaRepository.findAll();
    }

    // Obtener un programa por su código
    public Optional<Programa> getProgramaByCodigo(Integer codigo) {
        return programaRepository.findById(codigo);
    }

    // Crear un nuevo programa
    public Programa createPrograma(Programa programa) {
        return programaRepository.save(programa);
    }

    // Actualizar un programa existente
    public Programa updatePrograma(Integer codigo, Programa programaDetails) {
        return programaRepository.findById(codigo).map(programa -> {
            programa.setNombre(programaDetails.getNombre());
            //programa.setCreditos(programaDetails.getCreditos());
            programa.setFacultad(programaDetails.getFacultad());
            return programaRepository.save(programa);
        }).orElseThrow(() -> new RuntimeException("Programa no encontrado"));
    }

    // Eliminar un programa por su código
    public void deletePrograma(Integer codigo) {
        programaRepository.deleteById(codigo);
    }
}