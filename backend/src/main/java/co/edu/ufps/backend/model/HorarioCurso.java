package co.edu.ufps.backend.model;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "horario_curso") // Asegúrate de que este sea el nombre correcto de la tabla
public class HorarioCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dia")
    private String dia;

    // El campo 'hora' es problemático, ya que parece estar causando el error
    // Si no lo necesitas para la validación de horarios, considera omitirlo temporalmente
    @Column(name = "hora")
    private Integer hora;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;

    @ManyToOne
    @JoinColumn(name = "id_aula")
    private Aula aula;

    @ManyToOne // Cambiado de @OneToOne a @ManyToOne porque parece más lógico para este caso
    @JoinColumn(name = "id_curso")
    private Curso curso;
}