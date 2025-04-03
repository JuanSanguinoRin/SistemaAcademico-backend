package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Programa {
    private String nombre;
    @Id
    private Integer codigo;
    private Integer creditos;
    private String facultad;

    // Métodos de la clase
    public void modificarPrograma() {
        // Implementación del método
    }

    public void modificarPensum() {
        // Implementación del método
    }

}