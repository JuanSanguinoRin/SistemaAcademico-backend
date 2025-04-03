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

    // Métodos de la clase
    public void eliminarDatos() {
        // Implementación del método
    }

    public void modificarDatos() {
        // Implementación del método
    }

    public void solicitarRecurso() {
        // Implementación del método
    }

    public void desistir() {
        // Implementación del método
    }

    public void escribirMensaje() {
        // Implementación del método
    }

}