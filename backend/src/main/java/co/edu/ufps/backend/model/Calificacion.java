package co.edu.ufps.backend.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calificacion {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Date fecha;
    private String tipo; //P1, P2, P3, EX, H
    private Float nota;
    @OneToOne
    @JoinColumn(name = "estudiante")
    private EstudianteCurso estudianteCurso;

}