package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Mensaje;
import co.edu.ufps.backend.repository.MensajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MensajeService {
    @Autowired
    private final MensajeRepository mensajeRepository;

    public List<Mensaje> getAllMensajes() {
        return mensajeRepository.findAll();
    }

    public Optional<Mensaje> getMensajeById(Long id) {
        return mensajeRepository.findById(id);
    }

    public Mensaje createMensaje(Mensaje mensaje) {
        return mensajeRepository.save(mensaje);
    }

    public Mensaje updateMensaje(Long id, Mensaje mensajeDetails) {
        return mensajeRepository.findById(id).map(mensaje -> {
            mensaje.setRemitente(mensajeDetails.getRemitente());
            mensaje.setDestinatario(mensajeDetails.getDestinatario());
            mensaje.setContenido(mensajeDetails.getContenido());
            mensaje.setFechaEnvio(mensajeDetails.getFechaEnvio());
            mensaje.setLeido(mensajeDetails.getLeido());
            return mensajeRepository.save(mensaje);
        }).orElseThrow(() -> new RuntimeException("Mensaje not found"));
    }

    public void deleteMensaje(Long id) {
        mensajeRepository.deleteById(id);
    }
}