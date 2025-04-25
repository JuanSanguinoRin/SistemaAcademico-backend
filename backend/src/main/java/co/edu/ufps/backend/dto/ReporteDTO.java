package co.edu.ufps.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDTO {
    private String titulo;
    private String descripcion;
    private String tipoReporte;
    private Map<String, Object> parametros;
    private List<Map<String, Object>> datos;
}