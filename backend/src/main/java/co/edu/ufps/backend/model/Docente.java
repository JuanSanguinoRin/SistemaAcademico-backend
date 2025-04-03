package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Docente {
    @Id
    private Long codigoDocente;
    private String experiencia;
    @OneToOne
    @JoinColumn(name = "codigo_Docente")
    private Programa programa;
    @OneToOne
    @JoinColumn(name = "codigo_Persona")
    private Persona persona;

    // Métodos de la clase
    public void consultarHorario() {
        // Implementación del método
    }

    public void consultarNotasEstudiantes() {
        // Implementación del método
    }

    public void cambiarDatos() {
        // Implementación del método
    }

    public void consultarCursos() {
        // Implementación del método
    }

    public void definirHorario() {
        // Implementación del método
    }

}