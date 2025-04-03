package co.edu.ufps.backend.model;
import java.util.Date;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recurso {
    // Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String ubicacion;
    private String estadoMateria;

    
    // Methods from UML
    public void existir() {
        // Implementation
    }
    
    public void desechar() {
        // Implementation
    }
    
    public void crear() {
        // Implementation
    }

}
