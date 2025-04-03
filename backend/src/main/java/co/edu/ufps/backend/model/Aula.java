package co.edu.ufps.backend.model;
import jakarta.persistence.*;
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
    private String estado;
    private Boolean esExamen;



    // Métodos de la clase
    public void abrir() {
        // Implementación del método
    }

    public void cerrar() {
        // Implementación del método
    }

    public void fechaDisponibilidad() {
        // Implementación del método
    }


}