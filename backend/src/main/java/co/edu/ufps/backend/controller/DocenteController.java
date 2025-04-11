package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Calificacion;
import co.edu.ufps.backend.model.Curso;
import co.edu.ufps.backend.model.Docente;
import co.edu.ufps.backend.model.HorarioCurso;
import co.edu.ufps.backend.service.DocenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/docentes")
@RequiredArgsConstructor
public class DocenteController {

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

    // 1. Obtener todos los cursos de un docente
    @GetMapping("/{codigoDocente}/cursos")
    public ResponseEntity<List<Curso>> getCursosDelDocente(@PathVariable Long codigoDocente) {
        return ResponseEntity.ok(docenteService.getCursosDelDocente(codigoDocente));
    }

    // 2. Obtener todos los horarios de los cursos del docente
    @GetMapping("/{codigoDocente}/horarios")
    public ResponseEntity<List<HorarioCurso>> getHorariosDelDocente(@PathVariable Long codigoDocente) {
        return ResponseEntity.ok(docenteService.getHorariosByDocente(codigoDocente));
    }

    // 3. Obtener todas las calificaciones asociadas a cursos del docente
    @GetMapping("/{codigoDocente}/notas")
    public ResponseEntity<List<Calificacion>> getNotasDeCursosDocente(@PathVariable Long codigoDocente) {
        return ResponseEntity.ok(docenteService.getNotasDeCursosDocente(codigoDocente));
    }

    // 4. Definir un nuevo horario para un curso (crear)
    @PostMapping("/horarios")
    public ResponseEntity<HorarioCurso> definirHorario(@RequestBody HorarioCurso horarioCurso) {
        return ResponseEntity.ok(docenteService.definirHorarioCurso(horarioCurso));
    }

    // 5. Modificar un horario existente
    @PutMapping("/horarios/{idHorario}")
    public ResponseEntity<HorarioCurso> modificarHorario(@PathVariable Long idHorario, @RequestBody HorarioCurso datosActualizados) {
        return ResponseEntity.ok(docenteService.modificarHorarioCurso(idHorario, datosActualizados));
    }

}
