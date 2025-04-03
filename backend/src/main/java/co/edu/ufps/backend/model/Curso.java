package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    private String nombre;
    private String descripcion;
    private String contenido;
    @OneToOne
    @JoinColumn(name = "codigo_Docente")
    private Docente docente;
    private String objetivos;
    private String competencias;
    private Integer cupoMaximo;
    @ManyToOne
    @JoinColumn(name = "codigo_Asignatura")
    private Asignatura asignatura;
    @OneToOne
    @JoinColumn(name = "codigo_Programa")
    private Programa programa;
    @OneToOne
    @JoinColumn(name = "id_Semestre")
    private Semestre semestre;
    private Character grupo;
    private Boolean vacacional;

    // Métodos de la clase
    public void modificarCurso() {
        // Implementación del método
    }

    public void inscribirEstudiante() {
        // Implementación del método
    }

    public void cancelarInscripcion() {
        // Implementación del método
    }

    public void obtenerDetalles() {
        // Implementación del método
    }

    public void crearEvaluacion() {
        // Implementación del método
    }

    public void crearTarea() {
        // Implementación del método
    }

    public void modificarCalificacion() {
        // Implementación del método
    }

    public void generarAsistencia() {
        // Implementación del método
    }

    public void eliminarAsistencia() {
        // Implementación del método
    }

    public void modificarAsistencia() {
        // Implementación del método
    }

}