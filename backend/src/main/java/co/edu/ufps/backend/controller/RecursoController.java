package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Recurso;
import co.edu.ufps.backend.service.RecursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recursos")
@RequiredArgsConstructor
public class RecursoController {

    private final RecursoService recursoService;

    /**
     * Obtener todos los recursos
     */
    @GetMapping
    public ResponseEntity<List<Recurso>> getAllRecursos() {
        return ResponseEntity.ok(recursoService.getAllRecursos());
    }

    /**
     * Obtener un recurso por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Recurso> getRecursoById(@PathVariable Long id) {
        return recursoService.getRecursoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crear un nuevo recurso
     */
    @PostMapping
    public ResponseEntity<Recurso>
