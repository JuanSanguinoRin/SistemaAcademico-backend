package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;
import co.edu.ufps.backend.model.Foro;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clave primaria autogenerada

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEnvio;

    @OneToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona autor;

    @ManyToOne
    @JoinColumn(name = "foro_id", nullable = false)
    private Foro foro;

}