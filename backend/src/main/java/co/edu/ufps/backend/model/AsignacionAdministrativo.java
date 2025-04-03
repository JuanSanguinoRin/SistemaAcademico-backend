package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AsignacionAdministrativo {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "Id", nullable = false, unique = true)
    private PersonalAdministrativo personal;

    private Integer anio; // Año de la asignación

    
    // Métodos específicos
    public void asignaEmpleado() {
        // Implementación del método
    }
    
    public boolean verificarDisponibilidad() {
        // Implementación del método
        return false;
    }

}