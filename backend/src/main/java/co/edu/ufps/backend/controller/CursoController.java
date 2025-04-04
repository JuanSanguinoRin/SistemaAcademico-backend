package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Curso;
import co.edu.ufps.backend.model.Estudiante;
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

    @PostMapping("/{id}/inscribir")
    public ResponseEntity<Void> inscribirEstudiante(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        try {
            cursoService.inscribirEstudiante(id, estudiante);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{cursoId}/cancelar/{estudianteId}")
    public ResponseEntity<Void> cancelarInscripcion(@PathVariable Long cursoId, @PathVariable Long estudianteId) {
        cursoService.cancelarInscripcion(cursoId, estudianteId);
        return ResponseEntity.noContent().build();
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

    @PostMapping("/{id}/evaluacion")
    public ResponseEntity<Void> crearEvaluacion(@PathVariable Long id, @RequestBody String evaluacionData) {
        try {
            cursoService.crearEvaluacion(id, evaluacionData);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/tarea")
    public ResponseEntity<Void> crearTarea(@PathVariable Long id, @RequestBody String tareaData) {
        try {
            cursoService.crearTarea(id, tareaData);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{cursoId}/calificacion/{estudianteId}")
    public ResponseEntity<Void> modificarCalificacion(@PathVariable Long cursoId, @PathVariable Long estudianteId, @RequestBody Float calificacion) {
        try {
            cursoService.modificarCalificacion(cursoId, estudianteId, calificacion);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/asistencia")
    public ResponseEntity<Void> generarAsistencia(@PathVariable Long id, @RequestBody String fecha) {
        try {
            cursoService.generarAsistencia(id, fecha);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}