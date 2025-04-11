package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pensum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer creditosTotales;
    @OneToOne
    @JoinColumn(name = "codigo_Programa")
    private Programa programa;

    private Integer duracion;
    private Integer anio;



}