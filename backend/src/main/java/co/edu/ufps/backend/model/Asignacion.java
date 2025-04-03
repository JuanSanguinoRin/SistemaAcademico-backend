package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

//YA ESTAAAAAAAAAAAAAAAAAAAAAAAAAA
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Asignacion {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "codigoDocente")
    private Docente docente;

    @ManyToOne
    @JoinColumn(name = "codigo")
    private Curso curso;
    
    // Constructor

    
    // Métodos específicos
    public void asignarDocente() {
        // Implementación del método
    }
    
    public boolean verificarDisponibilidad() {
        // Implementación del método
        return false;
    }
    

}