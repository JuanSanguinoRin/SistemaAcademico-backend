package co.edu.ufps.backend.model;

import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asignatura {
    @Id
    private Long codigo;
    private String nombre;
    private Integer semestre;
    private Integer horas;
    private String tipoAsignatura;
    @ManyToOne
    @JoinColumn(name = "id_Pensum")
    private Pensum asignatura;

    // Métodos de la clase
    public void agregarPrerrequisito() {
        // Implementación del método
    }

    public void eliminarPrerrequisito() {
        // Implementación del método
    }

}