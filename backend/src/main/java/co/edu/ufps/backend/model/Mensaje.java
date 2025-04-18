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






}