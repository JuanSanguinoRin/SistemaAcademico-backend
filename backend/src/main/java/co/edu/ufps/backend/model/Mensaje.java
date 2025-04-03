package co.edu.ufps.backend.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mensaje {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "remitente")
    private Persona remitente;
    @ManyToOne
    @JoinColumn(name = "destinatario")
    private Persona destinatario;
    private String contenido;
    private Date fechaEnvio;
    private Boolean leido;

    public Mensaje(Persona remitente, Persona destinatario, String contenido,
                   Date fechaEnvio, boolean leido) {
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.contenido = contenido;
        this.fechaEnvio = fechaEnvio;
        this.leido = leido;
    }

    // Métodos
    public void marcarComoLeido() {
        // Implementación
    }
    
    public Mensaje responder(String contenido) {
        // Implementación
        return new Mensaje(this.destinatario, this.remitente, contenido, new Date(), false);
    }
    
    public Mensaje reenviar(List<Persona> destinatarios) {
        // Implementación (nota: Usuario no está definido en las 10 primeras clases)
        return null;
    }
    
    public void eliminar() {
        // Implementación
    }

}