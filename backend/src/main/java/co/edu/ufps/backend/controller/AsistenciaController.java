package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Asistencia;
import co.edu.ufps.backend.service.AsistenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/asistencias")
@RequiredArgsConstructor
public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    /**
     * Obtener todas las asistencias
     * @return Lista de asistencias
     */
    @GetMapping
    public List<Asistencia> getAllAsistencias() {
        return asistenciaService.getAllAsistencias();
    }

    /**
     * Obtener una asistencia por su ID
     * @param id ID de la asistencia
     * @return La asistencia encontrada
     */
    @GetMapping("/{id}")
    public Asistencia getAsistenciaById(@PathVariable Long id) {
        return asistenciaService.getAsistenciaById(id)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada"));
    }

    /**
     * Registrar una nueva asistencia
     * @param estudianteCursoId ID del estudiante en el curso
     * @param asistenciaInput Datos de la asistencia a registrar
     * @return La asistencia registrada
     */
    @PostMapping("/{estudianteCursoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Asistencia registrarAsistencia(@PathVariable Long estudianteCursoId, @RequestBody Asistencia asistenciaInput) {
        return asistenciaService.registrarAsistencia(estudianteCursoId, asistenciaInput);
    }

    /**
     * Actualizar una asistencia existente
     * @param id ID de la asistencia a actualizar
     * @param asistenciaDetails Detalles actualizados de la asistencia
     * @return La asistencia actualizada
     */
    @PutMapping("/{id}")
    public Asistencia updateAsistencia(@PathVariable Long id, @RequestBody Asistencia asistenciaDetails) {
        return asistenciaService.updateAsistencia(id, asistenciaDetails);
    }

    /**
     * Eliminar una asistencia por su ID
     * @param id ID de la asistencia a eliminar
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAsistencia(@PathVariable Long id) {
        asistenciaService.deleteAsistencia(id);
    }

    /**
     * Justificar una inasistencia
     * @param asistenciaId ID de la asistencia a justificar
     * @param excusa Texto con la justificación
     * @return La asistencia con la excusa registrada
     */
    @PutMapping("/justificar/{asistenciaId}")
    public Asistencia justificarInasistencia(@PathVariable Long asistenciaId, @RequestParam String excusa) {
        return asistenciaService.justificarInasistencia(asistenciaId, excusa);
    }

    /**
     * Buscar asistencias por una fecha específica
     * @param fecha Fecha para filtrar asistencias
     * @return Lista de asistencias en la fecha especificada
     */
    @GetMapping("/fecha")
    public List<Asistencia> getAsistenciasByFecha(@RequestParam Date fecha) {
        return asistenciaService.getAsistenciasByFecha(fecha);
    }
}
