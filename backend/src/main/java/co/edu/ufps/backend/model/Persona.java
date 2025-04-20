package co.edu.ufps.backend.model;

import java.util.Date;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cedula;
    private String nombre;
    private Long telefono;
    private Date fechaNacimiento;
    private String correoElectronico;
    private String correoInstitucional;
    private String direccion;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
}
