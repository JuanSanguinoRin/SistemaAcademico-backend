package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Foro;
import co.edu.ufps.backend.service.ForoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foros")
@RequiredArgsConstructor
public class ForoController {

    private final ForoService foroService;

    /**
     * Obtener todos los foros
     */
    @GetMapping
    public ResponseEntity<List<Foro>> getAllForos() {
        return ResponseEntity.ok(foroService.getAllForos());
    }

    /**
     * Obtener un foro por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Foro> getForoById(@PathVariable Long id) {
        return foroService.getForoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crear un nuevo foro
     */
    @PostMapping
    public ResponseEntity<Foro> createForo(@RequestBody Foro foro) {
        Foro nuevoForo = foroService.createForo(foro);
        return ResponseEntity.ok(nuevoForo);
    }

    /**
     * Actualizar un foro existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Foro> updateForo(@PathVariable Long id, @RequestBody Foro foroDetails) {
        Foro foroActualizado = foroService.updateForo(id, foroDetails);
        return ResponseEntity.ok(foroActualizado);
    }

    /**
     * Eliminar un foro por ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForo(@PathVariable Long id) {
        foroService.deleteForo(id);
        return ResponseEntity.noContent().build();
    }
}
