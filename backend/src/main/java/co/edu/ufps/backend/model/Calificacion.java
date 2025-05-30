package co.edu.ufps.backend.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoEvaluacion tipo; //P1, P2, P3, EX, H

    private Float nota;
    @ManyToOne
    @JoinColumn(name = "estudiante")
    private EstudianteCurso estudianteCurso;

    private String retroalimentacion;

}