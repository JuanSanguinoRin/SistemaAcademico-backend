package co.edu.ufps.backend.model;

import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long codigo;
    private String nombre;
    private Integer semestre;
    private Integer creditos;
    private Integer horas;
    private String tipoAsignatura;
    @ManyToOne
    @JoinColumn(name = "id_Pensum")
    
    private Pensum pensum;



}