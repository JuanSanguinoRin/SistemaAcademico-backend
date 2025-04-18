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



    public Calificacion registrarCalificacion(Calificacion calificacion) {
        // 1. Verificar que venga con estudianteCurso
        if (calificacion.getEstudianteCurso() == null || calificacion.getEstudianteCurso().getId() == null) {
            throw new RuntimeException("Debe especificar el estudianteCurso con su ID.");
        }

        Long estudianteCursoId = calificacion.getEstudianteCurso().getId();

        // 2. Buscar EstudianteCurso desde la base de datos
        EstudianteCurso ec = estudianteCursoService.getById(estudianteCursoId);

        // 3. Validar estado "Cursando"
        if (!"Cursando".equalsIgnoreCase(ec.getEstado())) {
            throw new RuntimeException("El estudiante no está en estado 'Cursando'.");
        }

        // 4. Verificar si ya existe una calificación de ese tipo
        boolean tipoYaExiste = this.tipoYaExisteParaEstudianteCurso(estudianteCursoId, calificacion.getTipo());
        if (tipoYaExiste) {
            throw new RuntimeException("Ya existe una calificación de tipo '" + calificacion.getTipo() + "' para este estudiante.");
        }

        // 5. Asociar el EstudianteCurso real (opcional si ya viene completo)
        calificacion.setEstudianteCurso(ec);

        // 6. Guardar
        return calificacionRepository.save(calificacion);
    }

    //por pRobar
    public Calificacion modificarCalificacion(
            Long calificacionId,
            String nombre,
            String tipo,
            Float nota,
            Date fecha
    ) {
        Calificacion calificacion = this.getById(calificacionId);

        // Opcional: validar si el nuevo tipo ya existe en otra calificación
        if (!calificacion.getTipo().equalsIgnoreCase(tipo)) {
            boolean tipoYaExiste = this.tipoYaExisteParaEstudianteCurso(
                    calificacion.getEstudianteCurso().getId(), tipo
            );
            if (tipoYaExiste) {
                throw new RuntimeException("Ya existe una calificación de tipo '" + tipo + "' para este estudiante.");
            }
        }

        calificacion.setNombre(nombre);
        calificacion.setTipo(tipo);
        calificacion.setNota(nota);
        calificacion.setFecha(fecha);

        return calificacionRepository.save(calificacion);
    }


    public Calificacion guardarCalificacion(Calificacion calificacion) {
        return calificacionRepository.save(calificacion);
    }

    public Calificacion asignarCalificacion(Long estudianteId, Long cursoId, String nombre, String tipo, Float nota, Date fecha) {

        return null;

    }
}