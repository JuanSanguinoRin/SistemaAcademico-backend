package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Asignacion;
import co.edu.ufps.backend.model.Curso;
import co.edu.ufps.backend.model.Docente;
import co.edu.ufps.backend.repository.AsignacionRepository;
import co.edu.ufps.backend.repository.CursoRepository;
import co.edu.ufps.backend.repository.DocenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.ufps.backend.model.EstudianteCurso;
import co.edu.ufps.backend.model.Calificacion;
import co.edu.ufps.backend.model.Curso;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsignacionService {

    @Autowired
    private final AsignacionRepository asignacionRepository;

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


    //TODA ESTA MONDA ESTA MAL, AQUI SE LLAMA AL METODO DESDE LA CLASE ASIGNACION, NO SE HACE DESDE CERO
    //MENDOZA ES UN REVERENDO PENDEJO
    /*public Calificacion crearCalificacion(
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

    public Calificacion modificarCalificacion(Long id, String nombre, String tipo, Float nota, Date fecha) {
        Calificacion calificacion = getById(id);

        if (nombre != null) calificacion.setNombre(nombre);
        if (tipo != null) calificacion.setTipo(tipo);
        if (nota != null) calificacion.setNota(nota);
        if (fecha != null) calificacion.setFecha(fecha);

        return calificacionRepository.save(calificacion);
    }*/



    public boolean verificarDisponibilidad() {

        return false;
    }
}