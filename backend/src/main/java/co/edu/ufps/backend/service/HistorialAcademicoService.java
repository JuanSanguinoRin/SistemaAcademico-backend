package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.HistorialAcademico;
import co.edu.ufps.backend.repository.HistorialAcademicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistorialAcademicoService {
    @Autowired
    private final HistorialAcademicoRepository historialAcademicoRepository;

    public List<HistorialAcademico> getAllHistoriales() {
        return historialAcademicoRepository.findAll();
    }

    public Optional<HistorialAcademico> getHistorialById(Long id) {
        return historialAcademicoRepository.findById(id);
    }

    public HistorialAcademico createHistorial(HistorialAcademico historial) {
        return historialAcademicoRepository.save(historial);
    }

    public HistorialAcademico updateHistorial(Long id, HistorialAcademico historialDetails) {
        return historialAcademicoRepository.findById(id).map(historial -> {
            historial.setEstudiante(historialDetails.getEstudiante());
            historial.setCreditosAprobados(historialDetails.getCreditosAprobados());
            return historialAcademicoRepository.save(historial);
        }).orElseThrow(() -> new RuntimeException("Historial not found"));
    }

    public void deleteHistorial(Long id) {
        historialAcademicoRepository.deleteById(id);
    }
}

