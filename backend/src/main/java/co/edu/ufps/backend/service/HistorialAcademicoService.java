package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.HistorialAcademico;
import co.edu.ufps.backend.repository.HistorialAcademicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistorialAcademicoService {

    private final HistorialAcademicoRepository historialAcademicoRepository;

    public List<HistorialAcademico> getAllHistoriales() {
        return historialAcademicoRepository.findAll();
    }

    public Optional<HistorialAcademico> getHistorialById(Long id) {
        return historialAcademicoRepository.findById(id);
    }

    public HistorialAcademico getByEstudianteId(Long estudianteId) {
        return historialAcademicoRepository.findByEstudianteCodigoEstudiante(estudianteId)
                .orElseThrow(() -> new RuntimeException("Historial académico no encontrado"));
    }

    public HistorialAcademico createHistorial(HistorialAcademico historial) {
        // Si el historial no tiene créditos asignados, se inicializan en 0.
        if (historial.getCreditosAprobados() == null) {
            historial.setCreditosAprobados(0);
        }
        return historialAcademicoRepository.save(historial);
    }

    public HistorialAcademico updateHistorial(Long id, HistorialAcademico historialDetails) {
        return historialAcademicoRepository.findById(id).map(historial -> {
            historial.setEstudiante(historialDetails.getEstudiante());
            // Actualizamos los créditos aprobados solo si se proporcionan, de lo contrario se mantienen los actuales.
            if (historialDetails.getCreditosAprobados() != null) {
                historial.setCreditosAprobados(historialDetails.getCreditosAprobados());
            }
            return historialAcademicoRepository.save(historial);
        }).orElseThrow(() -> new RuntimeException("Historial no encontrado"));
    }

    public void deleteHistorial(Long id) {
        historialAcademicoRepository.deleteById(id);
    }

    // Lógica de ejemplo para calcular créditos aprobados:
    // Se asume que el estudiante aprobó 6 cursos y cada uno otorga 5 créditos.
    public HistorialAcademico calcularCreditosAprobados(Long id) {
        return historialAcademicoRepository.findById(id).map(historial -> {
            int cursosAprobados = 6; // Valor simulado; en una implementación real se obtendría de otra entidad o lógica.
            int creditosCalculados = cursosAprobados * 5;
            historial.setCreditosAprobados(creditosCalculados);
            return historialAcademicoRepository.save(historial);
        }).orElseThrow(() -> new RuntimeException("Historial no encontrado"));
    }

    // Método que retorna los créditos aprobados actuales sin modificar el registro.
    public Integer obtenerCreditosAprobados(Long id) {
        return historialAcademicoRepository.findById(id)
                .map(HistorialAcademico::getCreditosAprobados)
                .orElse(null);
    }
}
