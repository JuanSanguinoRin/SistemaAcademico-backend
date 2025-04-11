package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idEstudianteCurso", nullable = false)
    private EstudianteCurso estudianteCurso;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    private String estado;
    private String excusa;


    

}
