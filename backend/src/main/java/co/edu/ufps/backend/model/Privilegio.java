package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Privilegio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clave primaria autogenerada

    private String nombre;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol; // Cada Privilegio pertenece a un Rol

}