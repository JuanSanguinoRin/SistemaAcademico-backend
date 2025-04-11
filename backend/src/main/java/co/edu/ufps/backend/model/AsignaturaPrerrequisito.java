package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;
//YA ESTAAAAAAAAAAAAAAAAAAAAAAAAAA

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignaturaPrerrequisito {

    @Id
    private Long codigo;
    @OneToOne
    @JoinColumn(name = "codigo_Asignatura")
    private Asignatura asignatura;

    @OneToOne
    @JoinColumn(name = "codigo_AsignaturaPrerrequisito")
    private Asignatura prerrequisito;





}
