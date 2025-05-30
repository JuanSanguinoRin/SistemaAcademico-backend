package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Calificacion;
import co.edu.ufps.backend.model.EstudianteCurso;
import co.edu.ufps.backend.model.HistorialAcademico;
import co.edu.ufps.backend.model.TipoEvaluacion;
import co.edu.ufps.backend.repository.EstudianteCursoRepository;
import co.edu.ufps.backend.repository.HistorialAcademicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    // Metodo que retorna los créditos aprobados actuales sin modificar el registro.
    public Integer calcularCreditosAprobados(Long id) {

        List<EstudianteCurso> cursosAprobados = estudianteCursoService.getCursosAprobadosByEstudiante(id);

        if (cursosAprobados.isEmpty()) {
            return 0;
        }

        int totalCreditos = 0;

        for (EstudianteCurso ec : cursosAprobados) {
            // Obtenemos la nota definitiva del curso

            int creditos = ec.getCurso().getAsignatura().getCreditos();
            totalCreditos += creditos;

        }

        return totalCreditos;

    }

    public Double calcularPromedioPonderado(Long estudianteId) {
        // Obtener cursos aprobados
        List<EstudianteCurso> cursosAprobados = estudianteCursoService.getCursosAprobadosByEstudiante(estudianteId);
        List<EstudianteCurso> cursosReprobados = estudianteCursoService.getCursosReprobadosByEstudiante(estudianteId);

        cursosAprobados.addAll(cursosReprobados);

        if (cursosAprobados.isEmpty()) {
            return 0.0;
        }

        double sumaPonderada = 0;
        int totalCreditos = 0;

        for (EstudianteCurso ec : cursosAprobados) {
            // Obtenemos la nota definitiva del curso

            int creditos = ec.getCurso().getAsignatura().getCreditos();
            sumaPonderada += calcularDefinitivaPorEstudianteCurso(ec.getId()) * creditos;
            totalCreditos += creditos;
        }

        return totalCreditos > 0 ? sumaPonderada / totalCreditos : 0.0f;
    }

    public List<EstudianteCurso> getAllEstudianteCursoByEstudiante(Long estudianteId)
    {

        return estudianteCursoService.getCursosAprobadosByEstudiante(estudianteId);

    }

    public List<Calificacion>  getCalificacionesByEstudianteCurso(Long estudianteCursoId)
    {

        return calificacionService.getCalificacionesByEstudianteCurso(estudianteCursoService.getById(estudianteCursoId));

    }

    public Float calcularDefinitivaPorEstudianteCurso(Long estudianteCursoId) {
        // Obtener todas las calificaciones asociadas al estudianteCurso
        List<Calificacion> calificaciones = calificacionService.getCalificacionesByEstudianteCurso(estudianteCursoId);

        if (calificaciones.isEmpty()) {
            throw new RuntimeException("El estudiante no tiene calificaciones registradas.");
        }

        // Revisar si existe una nota de tipo HA (Habilitación)
        Optional<Calificacion> ha = calificaciones.stream()
                .filter(c -> TipoEvaluacion.HA == c.getTipo())
                .findFirst();

        if (ha.isPresent()) {
            return ha.get().getNota(); // Se usa la habilitación directamente
        }

        // Buscar calificaciones necesarias para el cálculo tradicional
        Map<TipoEvaluacion, Float> notas = new HashMap<>();
        for (Calificacion c : calificaciones) {
            notas.put(c.getTipo(), c.getNota());
        }

        // Validar que existan P1, P2, P3 y EX para el cálculo
        if (!notas.containsKey(TipoEvaluacion.P1) ||
                !notas.containsKey(TipoEvaluacion.P2) ||
                !notas.containsKey(TipoEvaluacion.P3) ||
                !notas.containsKey(TipoEvaluacion.EX)) {
            throw new RuntimeException("Faltan calificaciones requeridas (P1, P2, P3 o EX).");
        }

        // Cálculo ponderado
        float definitiva = (notas.get(TipoEvaluacion.P1) * 0.2333f) +
                (notas.get(TipoEvaluacion.P2) * 0.2333f) +
                (notas.get(TipoEvaluacion.P3) * 0.2333f) +
                (notas.get(TipoEvaluacion.EX) * 0.3001f);

        return Math.round(definitiva * 100.0f) / 100.0f; // Redondear a 2 decimales
    }

}
