package co.edu.ufps.backend.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calificacion {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Date fecha;
    private String tipo;
    private Float nota;
    @OneToOne
    @JoinColumn(name = "estudiante")
    private EstudianteCurso estudianteCurso;
    
    // Methods from UML
    public void modificarEvaluacion() {
        // Implementation
    }
    
    public void eliminarEvaluacion() {
        // Implementation
    }
    
    public String obtenerDetalles() {
        // Implementation
        return "";
    }
    
    public void calificar() {
        // Implementation
    }

}