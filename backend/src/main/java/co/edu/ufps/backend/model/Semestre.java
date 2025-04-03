package co.edu.ufps.backend.model;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Semestre {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer anio;
    private Integer periodo;
    
    // Métodos específicos
    public void consultarSemestre() {
        // Implementación del método
    }

}