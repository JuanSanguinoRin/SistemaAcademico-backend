package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long codigoEstudiante;

    @ManyToOne
    @JoinColumn(name = "programa_id")
    private Programa programa;

    @OneToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;
    private String modalidad;
    private String jornada;

}