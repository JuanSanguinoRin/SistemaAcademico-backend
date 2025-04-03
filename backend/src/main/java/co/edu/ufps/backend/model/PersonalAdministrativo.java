package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "persona_id") // esta monda es que hereda

public class PersonalAdministrativo extends Persona {
    // Atributos (según UML)
    @Column(nullable = false, unique = true)
    private Long id;

    private String cargo;
    private String departamento;
    
    // Métodos según UML
    public void generarReporte() {
        // Implementación del método
    }
    
    public void enviarNotificacion() {
        // Implementación del método
    }
    

}