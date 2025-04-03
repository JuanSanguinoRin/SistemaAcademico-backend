package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.BandejaEntrada;
import co.edu.ufps.backend.repository.BandejaEntradaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BandejaEntradaService {
    @Autowired
    private final BandejaEntradaRepository bandejaEntradaRepository;

    public List<BandejaEntrada> getAllBandejasEntrada() {
        return bandejaEntradaRepository.findAll();
    }

    public Optional<BandejaEntrada> getBandejaEntradaById(Long id) {
        return bandejaEntradaRepository.findById(id);
    }

    public BandejaEntrada createBandejaEntrada(BandejaEntrada bandejaEntrada) {
        return bandejaEntradaRepository.save(bandejaEntrada);
    }

    public BandejaEntrada updateBandejaEntrada(Long id, BandejaEntrada bandejaEntradaDetails) {
        return bandejaEntradaRepository.findById(id).map(bandejaEntrada -> {
            bandejaEntrada.setMensajesNoLeidos(bandejaEntradaDetails.getMensajesNoLeidos());
            bandejaEntrada.setPersona(bandejaEntradaDetails.getPersona());
            bandejaEntrada.setCapacidad(bandejaEntradaDetails.getCapacidad());
            return bandejaEntradaRepository.save(bandejaEntrada);
        }).orElseThrow(() -> new RuntimeException("BandejaEntrada not found"));
    }

    public void deleteBandejaEntrada(Long id) {
        bandejaEntradaRepository.deleteById(id);
    }
}