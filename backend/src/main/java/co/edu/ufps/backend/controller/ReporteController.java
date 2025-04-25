package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.dto.ReporteDTO;
import co.edu.ufps.backend.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/rendimiento-academico")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE')")
    public ResponseEntity<ReporteDTO> getReporteRendimientoAcademico(
            @RequestParam Long programaId,
            @RequestParam Long semestreId) {
        return ResponseEntity.ok(reporteService.generarReporteRendimientoAcademico(programaId, semestreId));
    }

    @GetMapping("/asistencia")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE')")
    public ResponseEntity<ReporteDTO> getReporteAsistencia(
            @RequestParam Long asignaturaId) {
        return ResponseEntity.ok(reporteService.generarReporteAsistencia(asignaturaId));
    }
}