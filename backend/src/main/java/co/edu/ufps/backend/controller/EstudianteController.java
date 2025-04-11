package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Estudiante;
import co.edu.ufps.backend.model.Semestre;
import co.edu.ufps.backend.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {
    private final EstudianteService estudianteService;

    @GetMapping
    public List<Estudiante> getAllEstudiantes() {
        return estudianteService.getAllEstudiantes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> getEstudianteById(@PathVariable Long id) {
        return estudianteService.getEstudianteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/programa/{programaId}")
    public List<Estudiante> getEstudiantesByPrograma(@PathVariable Long programaId) {
        return estudianteService.getEstudiantesByPrograma(programaId);
    }

    @GetMapping("/persona/{personaId}")
    public ResponseEntity<Estudiante> getEstudianteByPersonaId(@PathVariable Long personaId) {
        return estudianteService.getEstudianteByPersonaId(personaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Estudiante createEstudiante(@RequestBody Estudiante estudiante) {
        return estudianteService.createEstudiante(estudiante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> updateEstudiante(@PathVariable Long id, @RequestBody Estudiante estudianteDetails) {
        try {
            Estudiante updatedEstudiante = estudianteService.updateEstudiante(id, estudianteDetails);
            return ResponseEntity.ok(updatedEstudiante);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstudiante(@PathVariable Long id) {
        estudianteService.deleteEstudiante(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/modificar-datos")
    public ResponseEntity<Estudiante> modificarDatosEstudiante(@PathVariable Long id, @RequestBody Estudiante estudianteDetails) {
        try {
            Estudiante updatedEstudiante = estudianteService.updateEstudiante(id, estudianteDetails);
            return ResponseEntity.ok(updatedEstudiante);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/calcular-ponderado")
    public ResponseEntity<Float> calcularPonderado(@PathVariable Long id) {
        try {
            Float promedio = estudianteService.calcularPonderado(id);
            return ResponseEntity.ok(promedio);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /*@GetMapping("/{id}/calcular-semestre")
    public ResponseEntity<Integer> calcularSemestre(@PathVariable Long id) {
        try {
            Integer semestre = estudianteService.calcularSemestre(id);
            return ResponseEntity.ok(semestre);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }*/


}