package co.edu.ufps.backend.controller;

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

    @PostMapping("/{id}/asistencia")
    public ResponseEntity<Void> agregarAsistencia(@PathVariable Long id, @RequestBody Map<String, Object> asistencia) {
        try {
            String fecha = (String) asistencia.get("fecha");
            Boolean asistio = (Boolean) asistencia.get("asistio");
            estudianteCursoService.agregarAsistencia(id, fecha, asistio);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/definitiva")
    public ResponseEntity<Float> calcularDefinitiva(@PathVariable Long id) {
        try {
            Float definitiva = estudianteCursoService.calcularDefinitiva(id);
            return ResponseEntity.ok(definitiva);
        }catch (RuntimeException e){

            return ResponseEntity.badRequest().build();

        }
    }

    @GetMapping("/{id}/rehabilitacion")
    public ResponseEntity<Boolean> comprobarRehabilitacion(@PathVariable Long id) {
        try {
            Boolean puedeRehabilitar = estudianteCursoService.comprobarRehabilitacion(id);
            return ResponseEntity.ok(puedeRehabilitar);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
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

    @PostMapping("/matricular")
    public ResponseEntity<Void> matricularCurso(
            @RequestParam Long estudianteId,
            @RequestParam Long cursoId) {
        try {
            estudianteCursoService.matricularCurso(estudianteId, cursoId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}