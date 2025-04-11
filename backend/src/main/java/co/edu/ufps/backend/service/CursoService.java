package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.*;
import co.edu.ufps.backend.repository.CalificacionRepository;
import co.edu.ufps.backend.repository.CursoRepository;
import co.edu.ufps.backend.repository.EstudianteCursoRepository;
import co.edu.ufps.backend.repository.EstudianteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CursoService {
    private final CursoRepository cursoRepository;
    private final EstudianteRepository estudianteRepository;
    private final EstudianteCursoRepository estudianteCursoRepository;
    private final CalificacionRepository calificacionRepository;


    public List<Curso> getAllCursos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> getCursoById(Long id) {
        return cursoRepository.findById(id);
    }

    public List<Curso> getCursosByPrograma(Long programaId) {
        return cursoRepository.findByProgramaId(programaId);
    }

    public List<Curso> getCursosBySemestre(Long semestreId) {
        return cursoRepository.findBySemestreId(semestreId);
    }

    public List<Curso> getCursosByDocente(Long docenteId) {
        return cursoRepository.findByDocenteId(docenteId);
    }

    public List<Curso> getCursosVacacionales(Boolean vacacional) {
        return cursoRepository.findByVacacional(vacacional);
    }

    // En CursoService.java
    public Optional<Asignatura> getAsignaturaByCurso(Long cursoId) {
        Optional<Curso> curso = cursoRepository.findById(cursoId);
        return curso.map(Curso::getAsignatura);
    }

    public Curso createCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Curso updateCurso(Long id, Curso cursoDetails) {
        return cursoRepository.findById(id).map(curso -> {
            curso.setNombre(cursoDetails.getNombre());
            curso.setDescripcion(cursoDetails.getDescripcion());
            curso.setContenido(cursoDetails.getContenido());
            curso.setDocente(cursoDetails.getDocente());
            curso.setObjetivos(cursoDetails.getObjetivos());
            curso.setCompetencias(cursoDetails.getCompetencias());
            curso.setCupoMaximo(cursoDetails.getCupoMaximo());
            curso.setAsignatura(cursoDetails.getAsignatura());
            curso.setPrograma(cursoDetails.getPrograma());
            curso.setSemestre(cursoDetails.getSemestre());
            curso.setGrupo(cursoDetails.getGrupo());
            curso.setVacacional(cursoDetails.getVacacional());
            return cursoRepository.save(curso);
        }).orElseThrow(() -> new RuntimeException("Curso not found"));
    }

    public void deleteCurso(Long id) {
        cursoRepository.deleteById(id);
    }

    public Curso modificarCurso(Long id, Curso cursoDetails) {
        return updateCurso(id, cursoDetails);
    }


    public EstudianteCurso inscribirEstudiante(Long cursoId, Long estudianteId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Validar si ya está inscrito
        Optional<EstudianteCurso> yaInscrito = estudianteCursoRepository.findByCursoIdAndEstudianteCodigoEstudiante(cursoId, estudianteId);
        if (yaInscrito.isPresent()) {
            throw new RuntimeException("El estudiante ya está inscrito en este curso");
        }

        // Crear nueva inscripción
        EstudianteCurso inscripcion = new EstudianteCurso();
        inscripcion.setCurso(curso);
        inscripcion.setEstudiante(estudiante);
        inscripcion.setEstado("Cursando");
        inscripcion.setHabilitacion(false);

        return estudianteCursoRepository.save(inscripcion);
    }

    public void cancelarInscripcion(Long cursoId, Long estudianteId) {
        EstudianteCurso inscripcion = estudianteCursoRepository
                .findByCursoIdAndEstudianteCodigoEstudiante(cursoId, estudianteId)
                .orElseThrow(() -> new RuntimeException("El estudiante no está inscrito en este curso."));

        estudianteCursoRepository.delete(inscripcion);
    }

    public Curso obtenerDetalles(Long cursoId) {
        return cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso not found"));
    }


    public Calificacion crearCalificacion(Long cursoId, Long estudianteId, Calificacion calificacionInput) {
        // Validar que exista inscripción del estudiante al curso
        EstudianteCurso ec = estudianteCursoRepository
                .findByCursoIdAndEstudianteCodigoEstudiante(cursoId, estudianteId)
                .orElseThrow(() -> new RuntimeException("El estudiante no está inscrito en este curso"));

        // Asignar la relación a la calificación
        Calificacion calificacion = new Calificacion();
        calificacion.setNombre(calificacionInput.getNombre());
        calificacion.setFecha(calificacionInput.getFecha());
        calificacion.setTipo(calificacionInput.getTipo());
        calificacion.setNota(calificacionInput.getNota());
        calificacion.setEstudianteCurso(ec);

        return calificacionRepository.save(calificacion);
    }

    public Calificacion modificarCalificacion(Long calificacionId, Calificacion detallesActualizados) {
        Calificacion calificacion = calificacionRepository.findById(calificacionId)
                .orElseThrow(() -> new RuntimeException("Calificación no encontrada"));

        // Actualizar los campos si vienen nuevos valores
        if (detallesActualizados.getNombre() != null)
            calificacion.setNombre(detallesActualizados.getNombre());

        if (detallesActualizados.getFecha() != null)
            calificacion.setFecha(detallesActualizados.getFecha());

        if (detallesActualizados.getTipo() != null)
            calificacion.setTipo(detallesActualizados.getTipo());

        if (detallesActualizados.getNota() != null)
            calificacion.setNota(detallesActualizados.getNota());

        return calificacionRepository.save(calificacion);
    }



}