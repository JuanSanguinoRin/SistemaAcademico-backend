package co.edu.ufps.backend.model;
import java.sql.Time;
import java.util.Date;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dia;
    private Date hora;

    @ManyToOne
    @JoinColumn(name = "id_aula")
    private Aula aula;

    @OneToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;

    private Time horaInicio;
    private Time horaFin;






}