package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Persona;
import co.edu.ufps.backend.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaService {

    @Autowired
    private final PersonaRepository personaRepository;

    // Obtener todas las personas
    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    // Obtener una persona por su cédula
    public Optional<Persona> getPersonaByCedula(Long cedula) {
        return personaRepository.findById(cedula);
    }

    // Crear una nueva persona
    public Persona createPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    // Actualizar una persona existente
    public Persona updatePersona(Long cedula, Persona personaDetails) {
        return personaRepository.findById(cedula).map(persona -> {
            persona.setNombre(personaDetails.getNombre());
            persona.setTelefono(personaDetails.getTelefono());
            persona.setFechaNacimiento(personaDetails.getFechaNacimiento());
            persona.setEdad(personaDetails.getEdad());
            persona.setCorreoElectronico(personaDetails.getCorreoElectronico());
            persona.setCorreoInstitucional(personaDetails.getCorreoInstitucional());
            persona.setDireccion(personaDetails.getDireccion());
            persona.setSexo(personaDetails.getSexo());
            return personaRepository.save(persona);
        }).orElseThrow(() -> new RuntimeException("Persona no encontrada"));
    }

    // Eliminar una persona por su cédula
    public void deletePersona(Long cedula) {
        personaRepository.deleteById(cedula);
    }
}
