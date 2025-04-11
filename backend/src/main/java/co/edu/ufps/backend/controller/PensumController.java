package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Asignatura;
import co.edu.ufps.backend.model.Pensum;
import co.edu.ufps.backend.service.PensumService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pensums")
@RequiredArgsConstructor
public class PensumController {

    @Autowired
    private final PensumService pensumService;

    // Obtener todos los pensums
    @GetMapping
    public List<Pensum> getAllPensums() {
        return pensumService.getAllPensums();
    }

    // Obtener un pensum por ID
    @GetMapping("/{id}")
    public Optional<Pensum> getPensumById(@PathVariable Long id) {
        return pensumService.getPensumById(id);
    }

    // Crear un nuevo pensum
    @PostMapping
    public Pensum createPensum(@RequestBody Pensum pensum) {
        return pensumService.createPensum(pensum);
    }

    // Actualizar un pensum
    @PutMapping("/{id}")
    public Pensum updatePensum(@PathVariable Long id, @RequestBody Pensum pensumDetails) {
        return pensumService.updatePensum(id, pensumDetails);
    }

    // Eliminar un pensum
    @DeleteMapping("/{id}")
    public void deletePensum(@PathVariable Long id) {
        pensumService.deletePensum(id);
    }

    // Agregar una asignatura a un pensum
    @PostMapping("/{id}/asignaturas")
    public Asignatura agregarAsignatura(@PathVariable Long id, @RequestBody Asignatura asignatura) {
        return pensumService.agregarAsignatura(id, asignatura);
    }

    // Eliminar una asignatura por su código
    @DeleteMapping("/asignaturas/{codigoAsignatura}")
    public void eliminarAsignatura(@PathVariable Long codigoAsignatura) {
        pensumService.eliminarAsignatura(codigoAsignatura);
    }

    // Modificar parcialmente un pensum (nombre y duración)
    @PatchMapping("/{id}")
    public Pensum modificarPensum(@PathVariable Long id,
                                  @RequestParam(required = false) String nombre,
                                  @RequestParam(required = false) Integer duracion) {
        return pensumService.modificarPensum(id, nombre, duracion);
    }
}
