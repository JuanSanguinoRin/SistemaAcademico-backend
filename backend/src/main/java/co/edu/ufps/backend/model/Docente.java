package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long codigoDocente;

    private String experiencia;

    // Un docente pertenece a un programa (un programa tiene varios docentes)
    @ManyToOne
    @JoinColumn(name = "codigo_programa", nullable = false)
    private Programa programa;

    // Un docente está asociado a una persona
    @OneToOne
    @JoinColumn(name = "cedula_persona", nullable = false, unique = true)
    private Persona persona;
}