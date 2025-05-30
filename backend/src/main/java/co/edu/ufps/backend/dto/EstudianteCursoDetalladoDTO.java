package co.edu.ufps.backend.dto;

import co.edu.ufps.backend.model.Calificacion;
import co.edu.ufps.backend.model.EstudianteCurso;

import java.util.List;

public class EstudianteCursoDetalladoDTO {
    private EstudianteCurso estudianteCurso;
    private List<Calificacion> calificaciones;
    private Float definitiva;

    // Constructor, Getters y Setters
    public EstudianteCursoDetalladoDTO(EstudianteCurso estudianteCurso, List<Calificacion> calificaciones, Float definitiva) {
        this.estudianteCurso = estudianteCurso;
        this.calificaciones = calificaciones;
        this.definitiva = definitiva;
    }
    // ... getters y setters ...

    public EstudianteCurso getEstudianteCurso() {
        return estudianteCurso;
    }

    public void setEstudianteCurso(EstudianteCurso estudianteCurso) {
        this.estudianteCurso = estudianteCurso;
    }

    public Float getDefinitiva() {
        return definitiva;
    }

    public void setDefinitiva(Float definitiva) {
        this.definitiva = definitiva;
    }

    public List<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }

}
