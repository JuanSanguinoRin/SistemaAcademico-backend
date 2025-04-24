package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Foro;
import co.edu.ufps.backend.model.Persona;
import co.edu.ufps.backend.model.Publicacion;
import co.edu.ufps.backend.service.PublicacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publicaciones")
@RequiredArgsConstructor
public class PublicacionController {

    private final PublicacionService publicacionService;

    /**
     * Obtener todas las publicaciones
     */
    @GetMapping
    public ResponseEntity<List<Publicacion>> getAllPublicaciones() {
        return ResponseEntity.ok(publicacionService.getAllPublicaciones());
    }

    /**
     * Obtener publicación por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Publicacion> getPublicacionById(@PathVariable Long id) {
        return publicacionService.getPublicacionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crear una nueva publicación (publicar en un foro)
     */
    @PostMapping("/publicar")
    public ResponseEntity<Publicacion> publicarEnForo(
            @RequestParam Long personaId,
            @RequestParam Long foroId,
            @RequestBody String contenido) {

        Persona persona = new Persona();
        persona.setId(personaId);

        Foro foro = new Foro();
        foro.setId(foroId);

        Publicacion nueva = publicacionService.publicar(persona, foro, contenido);
        return ResponseEntity.ok(nueva);
    }

    /**
     * Actualizar una publicación
     */
    @PutMapping("/{id}")
    public ResponseEntity<Publicacion> updatePublicacion(
            @PathVariable Long id,
            @RequestBody Publicacion publicacionDetails) {
        return ResponseEntity.ok(publicacionService.updatePublicacion(id, publicacionDetails));
    }

    /**
     * Eliminar una publicación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublicacion(@PathVariable Long id) {
        publicacionService.deletePublicacion(id);
        return ResponseEntity.noContent().build();
    }
}
