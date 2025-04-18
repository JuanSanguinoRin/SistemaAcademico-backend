package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Calificacion;
import co.edu.ufps.backend.model.HistorialAcademico;
import co.edu.ufps.backend.service.HistorialAcademicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historial")
@RequiredArgsConstructor
public class HistorialAcademicoController {

    private final HistorialAcademicoService historialAcademicoService;

    @GetMapping
    public List<HistorialAcademico> getAllHistoriales() {
        return historialAcademicoService.getAllHistoriales();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialAcademico> getHistorialById(@PathVariable Long id) {
        return historialAcademicoService.getHistorialById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public HistorialAcademico createHistorial(@RequestBody HistorialAcademico historial) {
        return historialAcademicoService.createHistorial(historial);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialAcademico> updateHistorial(@PathVariable Long id,
                                                              @RequestBody HistorialAcademico historialDetails) {
        try {
            HistorialAcademico updated = historialAcademicoService.updateHistorial(id, historialDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorial(@PathVariable Long id) {
        historialAcademicoService.deleteHistorial(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para calcular y actualizar los créditos aprobados con lógica simulada.
    /*@PutMapping("/{id}/calcular-creditos")
    public ResponseEntity<HistorialAcademico> calcularCreditos(@PathVariable Long id) {
        try {
            HistorialAcademico updated = historialAcademicoService.calcularCreditosAprobados(id);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }*/

    // Endpoint para obtener los créditos aprobados sin modificar el historial.
    @GetMapping("/{id}/creditos")
    public ResponseEntity<Integer> obtenerCreditos(@PathVariable Long id) {
        Integer creditos = historialAcademicoService.calcularCreditosAprobados(id);
        return creditos != null ? ResponseEntity.ok(creditos) : ResponseEntity.notFound().build();
    }

    // Nuevo endpoint para obtener calificaciones
    @GetMapping("/estudiante-curso/{estudianteCursoId}/calificaciones")
    public ResponseEntity<List<Calificacion>> getCalificacionesByEstudianteCurso(
            @PathVariable Long estudianteCursoId) {
        List<Calificacion> calificaciones = historialAcademicoService
                .getCalificacionesByEstudianteCurso(estudianteCursoId);
        return ResponseEntity.ok(calificaciones);
    }

}
