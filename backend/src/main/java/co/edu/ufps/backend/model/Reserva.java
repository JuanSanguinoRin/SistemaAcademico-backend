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
    

    
    // Métodos
    public void confirmarReserva() {
        // Implementación
    }
    
    public void cancelarReserva() {
        // Implementación
    }
    
    public void asignarHorario() {
        // Implementación
    }
    

}