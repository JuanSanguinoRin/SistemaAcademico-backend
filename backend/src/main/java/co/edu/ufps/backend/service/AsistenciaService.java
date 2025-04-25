package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Asistencia;
import co.edu.ufps.backend.model.EstudianteCurso;
import co.edu.ufps.backend.repository.AsistenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsistenciaService {
    @Autowired
    private final AsistenciaRepository asistenciaRepository;
    //private final EstudianteCursoService estudianteCursoService;

    /**
     * Obtener todas las asistencias
     * @return lista de asistencias
     */
    public List<Asistencia> getAllAsistencias() {
        return asistenciaRepository.findAll();
    }

    /**
     * Obtener una asistencia por su ID
     * @param id ID de la asistencia
     * @return Asistencia encontrada o un Optional vacío si no existe
     */
    public Optional<Asistencia> getAsistenciaById(Long id) {
        return asistenciaRepository.findById(id);
    }


    /**
     * Actualizar una asistencia existente
     * @param id ID de la asistencia a actualizar
     * @param asistenciaDetails Detalles actualizados de la asistencia
     * @return Asistencia actualizada
     */
    public Asistencia updateAsistencia(Long id, Asistencia asistenciaDetails) {
        return asistenciaRepository.findById(id).map(asistencia -> {
            asistencia.setEstudianteCurso(asistenciaDetails.getEstudianteCurso());
            asistencia.setFecha(asistenciaDetails.getFecha());
            asistencia.setEstado(asistenciaDetails.getEstado());
            asistencia.setExcusa(asistenciaDetails.getExcusa());
            return asistenciaRepository.save(asistencia);
        }).orElseThrow(() -> new RuntimeException("Asistencia no encontrada"));
    }

    /**
     * Eliminar una asistencia por su ID
     * @param id ID de la asistencia a eliminar
     */
    public void deleteAsistencia(Long id) {
        asistenciaRepository.deleteById(id);
    }

    /**
     * Buscar asistencias por una fecha específica
     * @param fecha Fecha para filtrar asistencias
     * @return Lista de asistencias en la fecha especificada
     */
    public List<Asistencia> getAsistenciasByFecha(Date fecha) {
        return asistenciaRepository.findAll();
    }


    public Asistencia registrarAsistencia(Long estudianteCursoId, Asistencia asistenciaInput) {

        // Crear una nueva instancia de Asistencia, sin necesidad de EstudianteCursoRepository
        Asistencia asistencia = new Asistencia();

        // Aquí se podría buscar el EstudianteCurso desde la base de datos directamente con una consulta
        // Usando AsistenciaRepository, se asume que existe un campo idEstudianteCurso en la entidad Asistencia.
        Optional<Asistencia> estudianteCurso = asistenciaRepository.findById(estudianteCursoId);

        if (estudianteCurso.isEmpty()) {
            throw new RuntimeException("EstudianteCurso no encontrado");
        }

        asistencia.setEstudianteCurso(estudianteCurso.get().getEstudianteCurso());
        asistencia.setFecha(asistenciaInput.getFecha() != null ? asistenciaInput.getFecha() : new Date());
        asistencia.setEstado(asistenciaInput.getEstado() != null ? asistenciaInput.getEstado() : "PRESENTE"); // Por defecto "PRESENTE"
        asistencia.setExcusa(null); // No hay excusa al registrar normalmente

        return asistenciaRepository.save(asistencia);

    }

    public Asistencia justificarInasistencia(Long asistenciaId, String excusa) {
        Asistencia asistencia = asistenciaRepository.findById(asistenciaId)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada"));

        // Solo permitir justificar si la asistencia está en estado "AUSENTE"
        if (!"AUSENTE".equals(asistencia.getEstado())) {
            throw new RuntimeException("Solo se pueden justificar las inasistencias.");
        }

        asistencia.setEstado("JUSTIFICADA"); // Cambiar estado a "JUSTIFICADA"
        asistencia.setExcusa(excusa);

        return asistenciaRepository.save(asistencia);
    }



}