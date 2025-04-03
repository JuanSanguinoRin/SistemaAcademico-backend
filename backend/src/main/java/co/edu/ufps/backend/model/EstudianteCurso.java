package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteCurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "codigoEstudiante", nullable = false)
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "codigo", nullable = false)
    private Curso curso;

    private String estado; // (Aprobado, Cursando, Perdido)
    private Boolean habilitacion;



    // Métodos de la clase
    public void agregarAsistencia() {
        // Implementación del método
    }

    public void calcularDefinitiva() {
        // Implementación del método
    }

    public void comprobarRehabilitacion() {
        // Implementación del método
    }

    public void cancelar() {
        // Implementación del método
    }

    public void matricularCurso() {
        // Implementación del método
    }

}