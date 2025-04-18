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
public class Notificacion {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String preView;
    private Date fechaEnvio;
    @OneToOne
    @JoinColumn(name = "mensaje")
    private Mensaje mensaje;
    @OneToOne
    @JoinColumn(name = "destinatario")
    private Persona destinatario;


    private Boolean abierta;



}