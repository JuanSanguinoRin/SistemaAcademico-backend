package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Persona;
import co.edu.ufps.backend.model.Recurso;
import co.edu.ufps.backend.model.Reserva;
import co.edu.ufps.backend.repository.PersonaRepository;
import co.edu.ufps.backend.repository.RecursoRepository;
import co.edu.ufps.backend.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaService {

    private final PersonaRepository personaRepository;
    private final ReservaService reservaService;
    private final RecursoRepository recursoRepository;

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

    //Gestion de las reservas desde la persona
    public List<Reserva> obtenerReservasDePersona(Long cedula) {
        if (!personaRepository.existsById(cedula)) {
            throw new RuntimeException("Persona con cédula " + cedula + " no existe.");
        }
        return reservaService.getReservasByPersona(cedula);
    }

    public Reserva crearReservaParaPersona(Long cedulaPersona, Long recursoId, Date dia, Date horaInicio, Date horaFin) {

        Persona persona = personaRepository.findById(cedulaPersona)
                .orElseThrow(() -> new RuntimeException("Persona con cédula " + cedulaPersona + " no encontrada"));

        Recurso recurso = recursoRepository.findById(recursoId)
                .orElseThrow(() -> new RuntimeException("Recurso con ID " + recursoId + " no encontrado"));

        // Verificar si el recurso ya está reservado en ese horario
        boolean estaOcupado = reservaService.estaRecursoOcupado(recursoId, dia, horaInicio, horaFin);
        if (estaOcupado) {
            throw new RuntimeException("El recurso ya está reservado en ese horario.");
        }

        // Crear nueva reserva
        Reserva reserva = new Reserva();
        reserva.setUsuario(persona);
        reserva.setRecurso(recurso);
        reserva.setDia(dia);
        reserva.setHoraInicio(horaInicio);
        reserva.setHoraFin(horaFin);

        return reservaService.createReserva(reserva);
    }

    // Y MENSJAE
}
