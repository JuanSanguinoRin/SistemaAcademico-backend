package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Programa;
import co.edu.ufps.backend.service.ProgramaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/programas")
@RequiredArgsConstructor
public class ProgramaController {

    private final ProgramaService programaService;

    @GetMapping
    public List<Programa> getAllProgramas() {
        return programaService.getAllProgramas();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Programa> getProgramaByCodigo(@PathVariable Integer codigo) {
        return programaService.getProgramaByCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Programa createPrograma(@RequestBody Programa programa) {
        return programaService.createPrograma(programa);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Programa> updatePrograma(@PathVariable Integer codigo, @RequestBody Programa programaDetails) {
        try {
            Programa updatedPrograma = programaService.updatePrograma(codigo, programaDetails);
            return ResponseEntity.ok(updatedPrograma);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletePrograma(@PathVariable Integer codigo) {
        programaService.deletePrograma(codigo);
        return ResponseEntity.noContent().build();
    }
}