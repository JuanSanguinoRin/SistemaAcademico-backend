package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Calificacion;
import co.edu.ufps.backend.model.Curso;
import co.edu.ufps.backend.model.Estudiante;
import co.edu.ufps.backend.model.EstudianteCurso;
import co.edu.ufps.backend.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController {
    private final CursoService cursoService;

    @GetMapping
    public List<Curso> getAllCursos() {
        return cursoService.getAllCursos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> getCursoById(@PathVariable Long id) {
        return cursoService.getCursoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/programa/{programaId}")
    public List<Curso> getCursosByPrograma(@PathVariable Long programaId) {
        return cursoService.getCursosByPrograma(programaId);
    }

    @GetMapping("/semestre/{semestreId}")
    public List<Curso> getCursosBySemestre(@PathVariable Long semestreId) {
        return cursoService.getCursosBySemestre(semestreId);
    }

    @GetMapping("/docente/{docenteId}")
    public List<Curso> getCursosByDocente(@PathVariable Long docenteId) {
        return cursoService.getCursosByDocente(docenteId);
    }

    @GetMapping("/vacacionales")
    public List<Curso> getCursosVacacionales(@RequestParam Boolean vacacional) {
        return cursoService.getCursosVacacionales(vacacional);
    }

    @PostMapping
    public Curso createCurso(@RequestBody Curso curso) {
        return cursoService.createCurso(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> updateCurso(@PathVariable Long id, @RequestBody Curso cursoDetails) {
        try {
            Curso updatedCurso = cursoService.updateCurso(id, cursoDetails);
            return ResponseEntity.ok(updatedCurso);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        cursoService.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/modificar")
    public ResponseEntity<Curso> modificarCurso(@PathVariable Long id, @RequestBody Curso cursoDetails) {
        try {
            Curso updatedCurso = cursoService.modificarCurso(id, cursoDetails);
            return ResponseEntity.ok(updatedCurso);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{cursoId}/inscribir/{estudianteId}")
    public ResponseEntity<?> inscribirEstudiante(
            @PathVariable Long cursoId,
            @PathVariable Long estudianteId) {
        try {
            EstudianteCurso inscripcion = cursoService.inscribirEstudiante(cursoId, estudianteId);
            return ResponseEntity.ok(inscripcion); // Devuelve la relación creada
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{cursoId}/cancelar-inscripcion/{estudianteId}")
    public ResponseEntity<?> cancelarInscripcion(
            @PathVariable Long cursoId,
            @PathVariable Long estudianteId) {
        try {
            cursoService.cancelarInscripcion(cursoId, estudianteId);
            return ResponseEntity.noContent().build(); // 204 - Eliminado correctamente
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/detalles")
    public ResponseEntity<Curso> obtenerDetalles(@PathVariable Long id) {
        try {
            Curso curso = cursoService.obtenerDetalles(id);
            return ResponseEntity.ok(curso);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{cursoId}/calificar/{estudianteId}")
    public ResponseEntity<?> calificarEstudiante(
            @PathVariable Long cursoId,
            @PathVariable Long estudianteId,
            @RequestBody Calificacion calificacion) {
        try {
            Calificacion creada = cursoService.crearCalificacion(cursoId, estudianteId, calificacion);
            return ResponseEntity.ok(creada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/calificaciones/{id}")
    public ResponseEntity<?> modificarCalificacion(
            @PathVariable Long id,
            @RequestBody Calificacion calificacion) {
        try {
            Calificacion actualizada = cursoService.modificarCalificacion(id, calificacion);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}