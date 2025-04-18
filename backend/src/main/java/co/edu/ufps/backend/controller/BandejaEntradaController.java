package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.BandejaEntrada;
import co.edu.ufps.backend.model.Mensaje;
import co.edu.ufps.backend.service.BandejaEntradaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bandeja-entrada")
@RequiredArgsConstructor
public class BandejaEntradaController {

    private final BandejaEntradaService bandejaEntradaService;

    // Obtener todas las bandejas de entrada
    @GetMapping
    public List<BandejaEntrada> getAllBandejasEntrada() {
        return bandejaEntradaService.getAllBandejasEntrada();
    }

    // Obtener bandeja de entrada por ID
    @GetMapping("/{id}")
    public Optional<BandejaEntrada> getBandejaEntradaById(@PathVariable Long id) {
        return bandejaEntradaService.getBandejaEntradaById(id);
    }

    // Crear nueva bandeja de entrada
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BandejaEntrada createBandejaEntrada(@RequestBody BandejaEntrada bandejaEntrada) {
        return bandejaEntradaService.createBandejaEntrada(bandejaEntrada);
    }

    // Actualizar bandeja de entrada por ID
    @PutMapping("/{id}")
    public BandejaEntrada updateBandejaEntrada(
            @PathVariable Long id,
            @RequestBody BandejaEntrada bandejaEntradaDetails) {
        return bandejaEntradaService.updateBandejaEntrada(id, bandejaEntradaDetails);
    }

    // Eliminar bandeja de entrada por ID
    @DeleteMapping("/{id}")
    public void deleteBandejaEntrada(@PathVariable Long id) {
        bandejaEntradaService.deleteBandejaEntrada(id);
    }

    // Obtener los mensajes no leídos de la bandeja de entrada
    @GetMapping("/{id}/mensajes-no-leidos")
    public List<Mensaje> obtenerMensajesNoLeidos(@PathVariable Long id) {
        BandejaEntrada bandejaEntrada = bandejaEntradaService.getBandejaEntradaById(id)
                .orElseThrow(() -> new RuntimeException("Bandeja de entrada no encontrada"));
        return bandejaEntradaService.obtenerMensajesNoLeidos(bandejaEntrada.getPersona().getId());
    }

}
