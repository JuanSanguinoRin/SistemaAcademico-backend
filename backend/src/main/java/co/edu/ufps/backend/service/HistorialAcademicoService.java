package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Calificacion;
import co.edu.ufps.backend.model.EstudianteCurso;
import co.edu.ufps.backend.model.HistorialAcademico;
import co.edu.ufps.backend.repository.EstudianteCursoRepository;
import co.edu.ufps.backend.repository.HistorialAcademicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistorialAcademicoService {

    private final HistorialAcademicoRepository historialAcademicoRepository;
    private final EstudianteCursoService estudianteCursoService;
    private final CalificacionService calificacionService;

    public List<HistorialAcademico> getAllHistoriales() {
        return historialAcademicoRepository.findAll();
    }

    public Optional<HistorialAcademico> getHistorialById(Long id) {
        return historialAcademicoRepository.findById(id);
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

    // Método que retorna los créditos aprobados actuales sin modificar el registro.
    public Integer calcularCreditosAprobados(Long id) {

        List<EstudianteCurso> cursosAprobados = estudianteCursoService.getCursosAprobadosByEstudiante(id);

        if (cursosAprobados.isEmpty()) {
            return 0;
        }

        int totalCreditos = 0;

        for (EstudianteCurso ec : cursosAprobados) {
            // Obtenemos la nota definitiva del curso

            int creditos = ec.getCurso().getCreditos();
            totalCreditos += creditos;

        }

        return totalCreditos;

    }

    public Double calcularPromedioPonderado(Long estudianteId) {
        // Obtener cursos aprobados
        List<EstudianteCurso> cursosAprobados = estudianteCursoService.getCursosAprobadosByEstudiante(estudianteId);

        if (cursosAprobados.isEmpty()) {
            return 0.0;
        }

        double sumaPonderada = 0;
        int totalCreditos = 0;

        for (EstudianteCurso ec : cursosAprobados) {
            // Obtenemos la nota definitiva del curso

            int creditos = ec.getCurso().getCreditos();
            sumaPonderada += estudianteCursoService.calcularDefinitiva(ec.getId()) * creditos;
            totalCreditos += creditos;
        }

        return totalCreditos > 0 ? sumaPonderada / totalCreditos : 0.0;
    }

}
