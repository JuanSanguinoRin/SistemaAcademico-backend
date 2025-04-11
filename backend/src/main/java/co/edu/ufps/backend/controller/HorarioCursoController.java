package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.HorarioCurso;
import co.edu.ufps.backend.service.HorarioCursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/horarios")
@RequiredArgsConstructor
public class HorarioCursoController {

    private final HorarioCursoService horarioCursoService;

    // Obtener todos los horarios
    @GetMapping
    public List<HorarioCurso> getAllHorarios() {
        return horarioCursoService.getAllHorarios();
    }

    // Obtener un horario por ID
    @GetMapping("/{id}")
    public ResponseEntity<HorarioCurso> getHorarioById(@PathVariable Long id) {
        Optional<HorarioCurso> horario = horarioCursoService.getHorarioById(id);
        return horario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Crear nuevo horario (valida si está disponible)
    @PostMapping
    public ResponseEntity<?> createHorario(@RequestBody HorarioCurso horario) {
        if (!horarioCursoService.horarioDisponible(horario)) {
            return ResponseEntity.badRequest().body("Conflicto de horario: el aula o el curso ya están ocupados.");
        }
        HorarioCurso nuevo = horarioCursoService.createHorario(horario);
        return ResponseEntity.ok(nuevo);
    }

    // Actualizar horario
    @PutMapping("/{id}")
    public ResponseEntity<HorarioCurso> updateHorario(@PathVariable Long id, @RequestBody HorarioCurso horarioDetails) {
        try {
            HorarioCurso actualizado = horarioCursoService.updateHorario(id, horarioDetails);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar horario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHorario(@PathVariable Long id) {
        horarioCursoService.deleteHorario(id);
        return ResponseEntity.noContent().build();
    }
}
