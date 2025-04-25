package co.edu.ufps.backend.service;

import co.edu.ufps.backend.dto.ReporteDTO;
import co.edu.ufps.backend.repository.EstudianteRepository;
import co.edu.ufps.backend.repository.DocenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final JdbcTemplate jdbcTemplate;
    private final EstudianteRepository estudianteRepository;
    private final DocenteRepository docenteRepository;

    public ReporteDTO generarReporteRendimientoAcademico(Long programaId, Long semestreId) {
        String sql = "SELECT e.codigo_estudiante, p.nombre, AVG(n.valor) as promedio " +
                "FROM estudiantes e " +
                "JOIN personas p ON e.persona_id = p.id " +
                "JOIN notas n ON n.estudiante_id = e.id " +
                "JOIN asignaturas a ON n.asignatura_id = a.id " +
                "WHERE e.programa_id = ? AND a.semestre_id = ? " +
                "GROUP BY e.id, p.nombre " +
                "ORDER BY promedio DESC";

        List<Map<String, Object>> datos = jdbcTemplate.queryForList(sql, programaId, semestreId);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("programaId", programaId);
        parametros.put("semestreId", semestreId);

        return new ReporteDTO(
                "Reporte de Rendimiento Académico",
                "Promedio de calificaciones por estudiante",
                "RENDIMIENTO_ACADEMICO",
                parametros,
                datos
        );
    }

    public ReporteDTO generarReporteAsistencia(Long asignaturaId) {
        String sql = "SELECT e.codigo_estudiante, p.nombre, " +
                "COUNT(a.id) as total_clases, " +
                "SUM(CASE WHEN a.asistio = true THEN 1 ELSE 0 END) as clases_asistidas, " +
                "ROUND((SUM(CASE WHEN a.asistio = true THEN 1 ELSE 0 END) * 100.0 / COUNT(a.id)), 2) as porcentaje " +
                "FROM asistencias a " +
                "JOIN estudiantes e ON a.estudiante_id = e.id " +
                "JOIN personas p ON e.persona_id = p.id " +
                "WHERE a.asignatura_id = ? " +
                "GROUP BY e.id, p.nombre " +
                "ORDER BY porcentaje DESC";

        List<Map<String, Object>> datos = jdbcTemplate.queryForList(sql, asignaturaId);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("asignaturaId", asignaturaId);

        return new ReporteDTO(
                "Reporte de Asistencia",
                "Porcentaje de asistencia por estudiante",
                "ASISTENCIA",
                parametros,
                datos
        );
    }
}