package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer nivelAcceso;
    private String modulo;
    @ManyToOne
    @JoinColumn(name = "cedula")
    private Persona persona;

    // Métodos específicos
    public void agregarPrivilegio() {
        // Implementación del método
    }
    
    public void eliminarPrivilegio() {
        // Implementación del método
    }
    
    public void actualizarPrivilegio() {
        // Implementación del método
    }
    
    public boolean consultarPrivilegio() {
        // Implementación del método
        return false;
    }

}