package co.edu.ufps.backend.model;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class HistorialAcademico {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "estudiante")
    private Estudiante estudiante;
    private Integer creditosAprobados;
    

    
    // Métodos
    public void calcularCreditosAprobados() {
        // Implementación
    }

    public void obtenerCreditosAprobados() {

    }
    

}

