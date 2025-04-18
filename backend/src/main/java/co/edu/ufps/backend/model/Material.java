package co.edu.ufps.backend.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material extends Recurso{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String estado;
    private String tipo;

    
    // Métodos
    public void reservar() {
        // Implementación
    }
    
    public void liberar() {
        // Implementación
    }
    
    public void devolver() {
        // Implementación
    }
    
    public void crearRecurso() {
        // Implementación
    }
    

}