package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.AsignaturaPrerrequisito;
import co.edu.ufps.backend.service.AsignaturaPrerrequisitoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prerrequisitos")
@RequiredArgsConstructor
public class AsignaturaPrerrequisitoController {

    private final AsignaturaPrerrequisitoService asignaturaPrerrequisitoService;

    // 🔹 Obtener todos los prerrequisitos
    @GetMapping
    public ResponseEntity<List<AsignaturaPrerrequisito>> getAll() {
        return ResponseEntity.ok(asignaturaPrerrequisitoService.getAllAsignaturasPrerrequisito());
    }

    // 🔹 Obtener un prerrequisito por ID
    @GetMapping("/{codigo}")
    public ResponseEntity<AsignaturaPrerrequisito> getById(@PathVariable Long id) {
        return asignaturaPrerrequisitoService.getAsignaturaPrerrequisitoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Crear una nueva relación de prerrequisito
    @PostMapping
    public ResponseEntity<AsignaturaPrerrequisito> create(@RequestBody AsignaturaPrerrequisito relacion) {
        return ResponseEntity.ok(asignaturaPrerrequisitoService.createAsignaturaPrerrequisito(relacion));
    }

    // 🔹 Actualizar una relación existente
    @PutMapping("/{codigo}")
    public ResponseEntity<AsignaturaPrerrequisito> update(
            @PathVariable Long codigo,
            @RequestBody AsignaturaPrerrequisito relacion) {
        return ResponseEntity.ok(asignaturaPrerrequisitoService.updateAsignaturaPrerrequisito(codigo, relacion));
    }

    // 🔹 Eliminar una relación de prerrequisito
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> delete(@PathVariable Long codigo) {
        asignaturaPrerrequisitoService.deleteAsignaturaPrerrequisito(codigo);
        return ResponseEntity.noContent().build();
    }

        @GetMapping("/asignatura/{asignaturaId}")
        public ResponseEntity<List<AsignaturaPrerrequisito>> getAllPrerrequisitosByAsignaturaId(
                @PathVariable Long asignaturaId) {
            List<AsignaturaPrerrequisito> prerrequisitos =
                    asignaturaPrerrequisitoService.getAllPrerrequisitosByAsignaturaId(asignaturaId);

            if (prerrequisitos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(prerrequisitos);
        }


}
