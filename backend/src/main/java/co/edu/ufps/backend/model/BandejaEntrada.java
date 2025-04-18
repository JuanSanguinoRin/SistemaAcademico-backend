package co.edu.ufps.backend.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BandejaEntrada {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer mensajesNoLeidos;
    @OneToOne
    @JoinColumn(name = "persona")
    private Persona persona;
    private Integer capacidad;

}