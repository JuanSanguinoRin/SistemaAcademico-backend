package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "persona_id")

public class PersonalAdministrativo extends Persona {
    // Atributos (según UML)
    @Column(nullable = false, unique = true)
    @Id
    private Long id;

    private String cargo;
    private String departamento;

}