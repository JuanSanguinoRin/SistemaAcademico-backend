package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Persona;
import co.edu.ufps.backend.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaService {

    private final PersonaRepository personaRepository;

    // Obtener todas las personas
    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    // Obtener una persona por su cédula
    public Optional<Persona> getPersonaByCedula(Long cedula) {
        return personaRepository.findById(cedula);
    }

    // Verificar si una persona existe por cédula
    public boolean existsByCedula(Long cedula) {
        return personaRepository.existsById(cedula);
    }

    // Crear una nueva persona
    public Persona createPersona(Persona persona) {
        if (existsByCedula(persona.getCedula())) {
            throw new RuntimeException("La persona con cédula " + persona.getCedula() + " ya existe.");
        }
        return personaRepository.save(persona);
    }

    // Actualizar una persona existente (modificarDatos)
    public Persona modificarDatos(Long cedula, Persona personaDetalles) {
        return personaRepository.findById(cedula).map(persona -> {
            if (personaDetalles.getNombre() != null) {
                persona.setNombre(personaDetalles.getNombre());
            }
            if (personaDetalles.getTelefono() != null) {
                persona.setTelefono(personaDetalles.getTelefono());
            }
            if (personaDetalles.getCorreoElectronico() != null) {
                if (!personaDetalles.getCorreoElectronico().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    throw new IllegalArgumentException("Correo electrónico inválido");
                }
                persona.setCorreoElectronico(personaDetalles.getCorreoElectronico());
            }
            if (personaDetalles.getDireccion() != null) {
                persona.setDireccion(personaDetalles.getDireccion());
            }
            return personaRepository.save(persona);
        }).orElseThrow(() -> new RuntimeException("Persona con cédula " + cedula + " no encontrada"));
    }

    // Eliminación lógica de una persona
    public void eliminarDatos(Long cedula) {
        Persona persona = personaRepository.findById(cedula)
                .orElseThrow(() -> new RuntimeException("Persona con cédula " + cedula + " no encontrada"));

        // Se eliminan los datos sensibles para anonimizar
        persona.setNombre("Usuario eliminado");
        persona.setTelefono(null);
        persona.setCorreoElectronico(null);
        persona.setCorreoInstitucional(null);
        persona.setDireccion(null);
        personaRepository.save(persona);
    }

    //FALA RECURSO Y MENSJAE
}
