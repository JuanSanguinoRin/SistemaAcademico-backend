package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Asignacion;
import co.edu.ufps.backend.model.Calificacion;
import co.edu.ufps.backend.model.Curso;
import co.edu.ufps.backend.model.Docente;
import co.edu.ufps.backend.repository.AsignacionRepository;
import co.edu.ufps.backend.repository.CursoRepository;
import co.edu.ufps.backend.repository.DocenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsignacionService {

    @Autowired
    private final AsignacionRepository asignacionRepository;
    private CalificacionService calificacionService;

//ESTO NO DEBERIA ESTAR AQUI
    @Autowired
    private final DocenteRepository docenteRepository;
//ESTA FOKIN MONDAD TAMPOCO
    @Autowired
    private final CursoRepository cursoRepository;


    /**
     * Obtener todas las asignaciones
     * @return lista de asignaciones
     */
    public List<Asignacion> getAllAsignaciones() {
        return asignacionRepository.findAll();
    }

    /**
     * Obtener una asignación por su id
     * @param id ID compuesto de la asignación
     * @return Asignación encontrada o un Optional vacío si no existe
     */
    public Optional<Asignacion> getAsignacionById(Long id) {
        return asignacionRepository.findById(id);
    }

    /**
     * Crear una nueva asignación
     * @param asignacion Objeto de tipo Asignacion
     * @return Asignación creada
     */
    public Asignacion createAsignacion(Asignacion asignacion) {
        return asignacionRepository.save(asignacion);
    }

    /**
     * Actualizar una asignación existente
     * @param id ID compuesto de la asignación a actualizar
     * @param asignacionDetails Detalles nuevos de la asignación
     * @return Asignación actualizada
     */
    public Asignacion updateAsignacion(Long id, Asignacion asignacionDetails) {
        return asignacionRepository.findById(id).map(asignacion -> {
            asignacion.setDocente(asignacionDetails.getDocente());
            asignacion.setCurso(asignacionDetails.getCurso());
            return asignacionRepository.save(asignacion);
        }).orElseThrow(() -> new RuntimeException("Asignación no encontrada"));
    }

    /**
     * Eliminar una asignación por su id
     * @param id ID compuesto de la asignación a eliminar
     */
    public void deleteAsignacion(Long id) {
        asignacionRepository.deleteById(id);
    }


    //FALTA CARGA HORARIA Y DISPONIBILIDAD HORARIA--------------------------------------
    public Asignacion asignarDocente(Long docenteId, Long cursoId) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Aquí puedes controlar si ya existe una asignación para ese curso
        Optional<Asignacion> asignacionExistente = asignacionRepository.findByCursoId(cursoId);
        if (asignacionExistente.isPresent()) {
            throw new RuntimeException("Ya existe un docente asignado a este curso");
        }

        Asignacion asignacion = new Asignacion();
        asignacion.setDocente(docente);
        asignacion.setCurso(curso);

        return asignacionRepository.save(asignacion);
    }

    //falta que llame ese metodo desde calificacion
    public Calificacion crearCalificacionDesdeAsignacion(
            Long docenteId,
            Long cursoId,
            Long estudianteId,
            String nombre,
            String tipo,
            Float nota,
            Date fecha
    ) {
        // 1. Validar que este docente está asignado al curso
        asignacionRepository.findByDocenteIdAndCursoId(docenteId, cursoId)
                .orElseThrow(() -> new RuntimeException("El docente no está asignado a este curso."));

        // 2. Delegar la lógica a CalificacionService
        return calificacionService.asignarCalificacion(estudianteId, cursoId, nombre, tipo, nota, fecha);
    }


    public boolean verificarDisponibilidad() {

        return false;
    }
}