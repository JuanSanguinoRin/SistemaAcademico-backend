package co.edu.ufps.backend.model;

import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "persona")
    private Persona usuario;

    private Date dia;
    private Date horaInicio;
    private Date horaFin;

    @OneToOne
    @JoinColumn(name = "recurso")
    private Recurso recurso;

    // ✅ Indica si la reserva es con fines de mantenimiento
    private Boolean esMantenimiento;

    // ✅ Indica si el recurso ha sido devuelto
    private Boolean devuelto;

    // ✅ Hora exacta en que el recurso fue devuelto (inicia como null)
    private Date horaDevolucion;
}
