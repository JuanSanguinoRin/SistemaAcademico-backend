package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

//YA ESTAAAAAAAAAAAAAAAAAAAAAAAAAA
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {
    @Id
    private Long codigoEstudiante;
    @ManyToOne
    @JoinColumn(name = "codigo")
    private Programa programa;
    private int creditosAprobados;
    private float promedioPonderado;
    @OneToOne
    @JoinColumn(name = "cedula")
    private Persona persona;

    // Métodos de la clase
    public void modificarDatosEstudiante() {
        // Implementación del método
    }

    public void incribirCurso() {
        // Implementación del método
    }

    public void cancelarCurso() {
        // Implementación del método
    }

    public void consultarHistorial() {
        // Implementación del método
    }

    public void desistir() {
        // Implementación del método
    }

    public void calcularPonderado() {
        // Implementación del método
    }

    public void calcularSemestre() {
        // Implementación del método
    }

    public void matricularCurso() {
        // Implementación del método
    }

    public void actualizarHistorialAcademico(Semestre semestre) {
        // Implementación del método
    }

}