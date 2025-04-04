package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String contenido;

    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Docente docente;

    private String objetivos;
    private String competencias;
    private Integer cupoMaximo;

    @ManyToOne
    @JoinColumn(name = "asignatura_id")
    private Asignatura asignatura;

    @ManyToOne
    @JoinColumn(name = "programa_id")
    private Programa programa;

    @ManyToOne
    @JoinColumn(name = "semestre_id")
    private Semestre semestre;

    private Character grupo;
    private Boolean vacacional;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<EstudianteCurso> estudiantes;
}