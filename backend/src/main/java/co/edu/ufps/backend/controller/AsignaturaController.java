package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Asignatura;
import co.edu.ufps.backend.model.AsignaturaPrerrequisito;
import co.edu.ufps.backend.service.AsignaturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignaturas")
@RequiredArgsConstructor
public class AsignaturaController {

    private final AsignaturaService asignaturaService;

    // 🔹 Obtener todas las asignaturas
    @GetMapping
    public ResponseEntity<List<Asignatura>> getAllAsignaturas() {
        return ResponseEntity.ok(asignaturaService.getAllAsignaturas());
    }

    // 🔹 Obtener una asignatura por código
    @GetMapping("/{codigo}")
    public ResponseEntity<Asignatura> getAsignaturaByCodigo(@PathVariable Long codigo) {
        return asignaturaService.getAsignaturaByCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Crear una nueva asignatura
    @PostMapping
    public ResponseEntity<Asignatura> createAsignatura(@RequestBody Asignatura asignatura) {
        return ResponseEntity.ok(asignaturaService.createAsignatura(asignatura));
    }

    // 🔹 Actualizar una asignatura existente
    @PutMapping("/{codigo}")
    public ResponseEntity<Asignatura> updateAsignatura(@PathVariable Long codigo, @RequestBody Asignatura asignatura) {
        return ResponseEntity.ok(asignaturaService.updateAsignatura(codigo, asignatura));
    }

    // 🔹 Eliminar una asignatura
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deleteAsignatura(@PathVariable Long codigo) {
        asignaturaService.deleteAsignatura(codigo);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Agregar un prerrequisito a una asignatura
    @PostMapping("/{codigo}/prerrequisitos/{codigoPrerrequisito}")
    public ResponseEntity<AsignaturaPrerrequisito> agregarPrerrequisito(
            @PathVariable Long codigo,
            @PathVariable Long codigoPrerrequisito) {
        AsignaturaPrerrequisito nuevo = asignaturaService.agregarPrerrequisito(codigo, codigoPrerrequisito);
        return ResponseEntity.ok(nuevo);
    }

    // 🔹 Eliminar un prerrequisito de una asignatura
    @DeleteMapping("/{codigo}/prerrequisitos/{codigoPrerrequisito}")
    public ResponseEntity<Void> eliminarPrerrequisito(
            @PathVariable Long codigo,
            @PathVariable Long codigoPrerrequisito) {
        asignaturaService.eliminarPrerrequisito(codigo, codigoPrerrequisito);
        return ResponseEntity.noContent().build();
    }
}
