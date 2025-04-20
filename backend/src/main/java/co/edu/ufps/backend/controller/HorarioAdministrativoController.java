package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.HorarioAdministrativo;
import co.edu.ufps.backend.model.AsignacionAdministrativo;
import co.edu.ufps.backend.service.HorarioAdministrativoService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/horarios-administrativos")
@RequiredArgsConstructor
public class HorarioAdministrativoController {
    private final HorarioAdministrativoService horarioAdministrativoService;

    @GetMapping
    public List<HorarioAdministrativo> getAllHorarios() {
        return horarioAdministrativoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioAdministrativo> getHorarioById(@PathVariable Long id) {
        return horarioAdministrativoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public HorarioAdministrativo createHorario(@RequestBody HorarioAdministrativo horario) {
        return horarioAdministrativoService.guardar(horario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioAdministrativo> updateHorario(@PathVariable Long id, @RequestBody HorarioAdministrativo horarioDetails) {
        try {
            HorarioAdministrativo updatedHorario = horarioAdministrativoService.guardar(horarioDetails);
            return ResponseEntity.ok(updatedHorario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHorario(@PathVariable Long id) {
        horarioAdministrativoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/generar")
    public ResponseEntity<HorarioAdministrativo> generarHorario(@RequestParam String dia,
                                                                @RequestParam String departamento,
                                                                @RequestParam LocalTime horaInicio,
                                                                @RequestParam LocalTime horaFin,
                                                                @RequestBody AsignacionAdministrativo asignacion) {
        try {
            HorarioAdministrativo nuevoHorario = horarioAdministrativoService.generarHorario(dia, departamento, horaInicio, horaFin, asignacion);
            return ResponseEntity.ok(nuevoHorario);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
