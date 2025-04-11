package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Asistencia;
import co.edu.ufps.backend.model.EstudianteCurso;
import co.edu.ufps.backend.service.EstudianteCursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/estudiante-cursos")
@RequiredArgsConstructor
public class EstudianteCursoController {
    private final EstudianteCursoService estudianteCursoService;

    @GetMapping
    public List<EstudianteCurso> getAllEstudianteCursos() {
        return estudianteCursoService.getAllEstudianteCursos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteCurso> getEstudianteCursoById(@PathVariable Long id) {
        return estudianteCursoService.getEstudianteCursoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estudiante/{estudianteId}")
    public List<EstudianteCurso> getEstudianteCursosByEstudiante(@PathVariable Long estudianteId) {
        return estudianteCursoService.getEstudianteCursosByEstudiante(estudianteId);
    }

    @GetMapping("/curso/{cursoId}")
    public List<EstudianteCurso> getEstudianteCursosByCurso(@PathVariable Long cursoId) {
        return estudianteCursoService.getEstudianteCursosByCurso(cursoId);
    }

    @GetMapping("/estudiante/{estudianteId}/curso/{cursoId}")
    public ResponseEntity<EstudianteCurso> getEstudianteCursoByEstudianteAndCurso(
            @PathVariable Long estudianteId, @PathVariable Long cursoId) {
        return estudianteCursoService.getEstudianteCursoByEstudianteAndCurso(estudianteId, cursoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public List<EstudianteCurso> getEstudianteCursosByEstado(@PathVariable String estado) {
        return estudianteCursoService.getEstudianteCursosByEstado(estado);
    }

    @PostMapping
    public EstudianteCurso createEstudianteCurso(@RequestBody EstudianteCurso estudianteCurso) {
        return estudianteCursoService.createEstudianteCurso(estudianteCurso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteCurso> updateEstudianteCurso(@PathVariable Long id, @RequestBody EstudianteCurso estudianteCursoDetails) {
        try {
            EstudianteCurso updatedEstudianteCurso = estudianteCursoService.updateEstudianteCurso(id, estudianteCursoDetails);
            return ResponseEntity.ok(updatedEstudianteCurso);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstudianteCurso(@PathVariable Long id) {
        estudianteCursoService.deleteEstudianteCurso(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/definitiva")
    public ResponseEntity<?> calcularDefinitiva(@PathVariable Long id) {
        try {
            Float nota = estudianteCursoService.calcularDefinitiva(id);
            return ResponseEntity.ok(Map.of("definitiva", nota));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/habilitacion")
    public ResponseEntity<?> comprobarRehabilitacion(@PathVariable Long id) {
        try {
            Boolean habilitado = estudianteCursoService.comprobarRehabilitacion(id);
            return ResponseEntity.ok(Map.of("habilitacion", habilitado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        try {
            estudianteCursoService.cancelar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/matricular/{estudianteId}/{cursoId}")
    public ResponseEntity<?> matricularCurso(
            @PathVariable Long estudianteId,
            @PathVariable Long cursoId) {
        try {
            EstudianteCurso ec = estudianteCursoService.matricularCurso(estudianteId, cursoId);
            return ResponseEntity.ok(ec);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}