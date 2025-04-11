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
     * Crear una nueva asistencia
     * @param asistencia Objeto de tipo Asistencia
     * @return Asistencia creada
     */
    public Asistencia createAsistencia(Asistencia asistencia) {
        return asistenciaRepository.save(asistencia);
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

        return null;

    }

    /*public Asistencia registrarAsistencia(Long estudianteCursoId, Asistencia asistenciaInput) {
        // Usamos el service de EstudianteCurso para obtener la relación
        EstudianteCurso ec = estudianteCursoService.getById(estudianteCursoId);

        Asistencia asistencia = new Asistencia();
        asistencia.setEstudianteCurso(ec);
        asistencia.setFecha(asistenciaInput.getFecha());
        asistencia.setEstado(asistenciaInput.getEstado());
        asistencia.setExcusa(asistenciaInput.getExcusa());

        return asistenciaRepository.save(asistencia);
    }*/
}