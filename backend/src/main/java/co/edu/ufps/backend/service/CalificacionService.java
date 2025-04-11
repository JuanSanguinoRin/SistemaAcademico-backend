package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Calificacion;
import co.edu.ufps.backend.model.EstudianteCurso;
import co.edu.ufps.backend.repository.CalificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalificacionService {
    @Autowired
    private final CalificacionRepository calificacionRepository;
    private final EstudianteCursoService estudianteCursoService;
    private final CalificacionService calificacionService;

    public List<Calificacion> getAllCalificaciones() {
        return calificacionRepository.findAll();
    }

    public Optional<Calificacion> getCalificacionById(Long id) {
        return calificacionRepository.findById(id);
    }

    public Calificacion getById(Long id) {
        return calificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calificación no encontrada"));
    }

    public Calificacion createCalificacion(Calificacion calificacion) {
        return calificacionRepository.save(calificacion);
    }

    public List<Calificacion> getCalificacionesByEstudianteCurso(EstudianteCurso curso)
    {

        return calificacionRepository.findByEstudianteCursoId(curso.getId());

    }

    public Calificacion updateCalificacion(Long id, Calificacion calificacionDetails) {
        return calificacionRepository.findById(id).map(calificacion -> {
            calificacion.setNombre(calificacionDetails.getNombre());
            calificacion.setFecha(calificacionDetails.getFecha());
            calificacion.setTipo(calificacionDetails.getTipo());
            calificacion.setNota(calificacionDetails.getNota());
            calificacion.setEstudianteCurso(calificacionDetails.getEstudianteCurso());
            return calificacionRepository.save(calificacion);
        }).orElseThrow(() -> new RuntimeException("Calificacion not found"));
    }

    public void deleteCalificacion(Long id) {
        calificacionRepository.deleteById(id);
    }

    public boolean tipoYaExisteParaEstudianteCurso(Long estudianteCursoId, String tipo) {
        return calificacionRepository.findByEstudianteCursoId(estudianteCursoId)
                .stream()
                .anyMatch(c -> c.getTipo().equalsIgnoreCase(tipo));
    }


    public Calificacion asignarCalificacion(
            Long estudianteId,
            Long cursoId,
            String nombre,
            String tipo,
            Float nota,
            Date fecha
    ) {
        // 1. Obtener la inscripción del estudiante al curso
        EstudianteCurso ec = estudianteCursoService.getInscripcion(cursoId, estudianteId);

        // 2. Validar estado "Cursando"
        if (!"Cursando".equalsIgnoreCase(ec.getEstado())) {
            throw new RuntimeException("El estudiante no está en estado 'Cursando' para este curso.");
        }

        // 3. Validar que no exista ya una calificación con ese tipo
        boolean tipoYaExiste = calificacionService.tipoYaExisteParaEstudianteCurso(ec.getId(), tipo);
        if (tipoYaExiste) {
            throw new RuntimeException("Ya existe una calificación de tipo '" + tipo + "' para este estudiante.");
        }

        // 4. Crear calificación desde cero
        Calificacion calificacion = new Calificacion();
        calificacion.setNombre(nombre);
        calificacion.setTipo(tipo);
        calificacion.setNota(nota);
        calificacion.setFecha(fecha);
        calificacion.setEstudianteCurso(ec);

        // 5. Guardar
        return calificacionService.guardarCalificacion(calificacion);
    }

    public Calificacion guardarCalificacion(Calificacion calificacion) {
        return calificacionRepository.save(calificacion);
    }
}