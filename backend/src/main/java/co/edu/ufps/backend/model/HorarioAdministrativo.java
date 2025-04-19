package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioAdministrativo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clave primaria autogenerada

    private String dia;
    private String departamento;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;

    @ManyToOne
    @JoinColumn(name = "asignacionAdmin_id", nullable = false)
    private AsignacionAdministrativo asignacionAdministrativo;



}