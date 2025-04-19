package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.*;
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
    private final AsistenciaService asistenciaService;
    private final DocenteService docenteService;
    private final CursoService cursoService;
    private final HorarioCursoService horarioCursoService;

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
        // 1. Obtener el docente desde su servicio
        Docente docente = docenteService.getDocenteByCodigo(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        // 2. Obtener el curso desde su servicio
        Curso curso = cursoService.getCursoById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // 3. Validar si ya hay una asignación existente (puedes crear un método en AsignacionService para esto si lo deseas)
        Optional<Asignacion> asignacionExistente = asignacionRepository.findByCursoId(cursoId);
        if (asignacionExistente.isPresent()) {
            throw new RuntimeException("Ya existe un docente asignado a este curso");
        }

        // 4. Validar que no haya cruce de horarios con otros cursos del docente
        List<HorarioCurso> horariosNuevoCurso = horarioCursoService.getAllHorarios().stream()
                .filter(h -> h.getCurso().getId().equals(cursoId))
                .toList();

        List<HorarioCurso> horariosDocente = docenteService.getHorariosByDocente(docenteId);

        for (HorarioCurso nuevo : horariosNuevoCurso) {
            for (HorarioCurso actual : horariosDocente) {
                if (horarioCursoService.seSolapan(nuevo, actual)) {
                    throw new RuntimeException("El docente tiene un conflicto de horario con el curso: " + actual.getCurso().getNombre());
                }
            }
        }

        // 5. Si pasa todas las validaciones, crear la asignación
        Asignacion asignacion = new Asignacion();
        asignacion.setDocente(docente);
        asignacion.setCurso(curso);

        return createAsignacion(asignacion);
    }




    public Asistencia registrarAsistencia(Long estudianteCursoId, Asistencia asistenciaInput) {
        return asistenciaService.registrarAsistencia(estudianteCursoId, asistenciaInput);
    }

}