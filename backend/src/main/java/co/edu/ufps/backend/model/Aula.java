package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aula extends Recurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer capacidad;
    private String ubicacion;
    private Integer dimensiones;
    private String tipo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoAula estado;

    private Boolean esExamen;

}