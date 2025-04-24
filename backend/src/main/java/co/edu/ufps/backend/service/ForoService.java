package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Foro;
import co.edu.ufps.backend.repository.ForoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForoService {
    @Autowired
    private final ForoRepository foroRepository;

    public List<Foro> getAllForos() {
        return foroRepository.findAll();
    }

    public Optional<Foro> getForoById(Long id) {
        return foroRepository.findById(id);
    }

    public Foro createForo(Foro foro) {
        return foroRepository.save(foro);
    }

    public Foro updateForo(Long id, Foro foroDetails) {
        return foroRepository.findById(id).map(foro -> {
            foro.setTema(foroDetails.getTema());
            foro.setDescripcion(foroDetails.getDescripcion());
            foro.setFechaCreacion(foroDetails.getFechaCreacion());
            return foroRepository.save(foro);
        }).orElseThrow(() -> new RuntimeException("Foro not found"));
    }

    public void deleteForo(Long id) {
        foroRepository.deleteById(id);


}