package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Material;
import co.edu.ufps.backend.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materiales")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    /**
     * Obtener todos los materiales
     */
    @GetMapping
    public ResponseEntity<List<Material>> getAllMateriales() {
        return ResponseEntity.ok(materialService.getAllMateriales());
    }

    /**
     * Obtener un material por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Material> getMaterialById(@PathVariable Long id) {
        return materialService.getMaterialById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crear un nuevo material
     */
    @PostMapping
    public ResponseEntity<Material> createMaterial(@RequestBody Material material) {
        return ResponseEntity.ok(materialService.createMaterial(material));
    }

    /**
     * Actualizar un material existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Material> updateMaterial(
            @PathVariable Long id,
            @RequestBody Material materialDetails) {
        return ResponseEntity.ok(materialService.updateMaterial(id, materialDetails));
    }

    /**
     * Eliminar un material por su ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }
}
