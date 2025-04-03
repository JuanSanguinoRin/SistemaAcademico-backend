package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "cedula", nullable = false)
    private Persona autor;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Foro foro;


    // Métodos específicos
    public void publicar() {
        // Implementación del método
    }
    
    public void citar() {
        // Implementación del método
    }

}