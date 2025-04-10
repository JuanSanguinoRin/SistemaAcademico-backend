package co.edu.ufps.backend.model;

import java.util.Date;
import jakarta.persistence.*;
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
    private Integer edad;
    private String correoElectronico;
    private String correoInstitucional;
    private String direccion;
    private String sexo;
}
