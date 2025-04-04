package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Docente;
import co.edu.ufps.backend.service.DocenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/docentes")
@RequiredArgsConstructor
public class docenteController {

    private final DocenteService docenteService;

    // Obtener todos los docentes
    @GetMapping
    public ResponseEntity<List<Docente>> getAllDocentes() {
        return ResponseEntity.ok(docenteService.getAllDocentes());
    }

    // Obtener un docente por su código
    @GetMapping("/{codigoDocente}")
    public ResponseEntity<Docente> getDocenteByCodigo(@PathVariable Long codigoDocente) {
        Optional<Docente> docente = docenteService.getDocenteByCodigo(codigoDocente);
        return docente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo docente
    @PostMapping
    public ResponseEntity<Docente> createDocente(@RequestBody Docente docente) {
        return ResponseEntity.ok(docenteService.createDocente(docente));
    }

    // Modificar un docente existente
    @PutMapping("/{codigoDocente}")
    public ResponseEntity<Docente> updateDocente(@PathVariable Long codigoDocente, @RequestBody Docente docenteDetails) {
        try {
            Docente updatedDocente = docenteService.cambiarDatos(codigoDocente, docenteDetails);
            return ResponseEntity.ok(updatedDocente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un docente por su código
    @DeleteMapping("/{codigoDocente}")
    public ResponseEntity<Void> deleteDocente(@PathVariable Long codigoDocente) {
        docenteService.deleteDocente(codigoDocente);
        return ResponseEntity.noContent().build();
    }
}
