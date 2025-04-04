package co.edu.ufps.backend.model;

import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Persona {
    @Id
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
