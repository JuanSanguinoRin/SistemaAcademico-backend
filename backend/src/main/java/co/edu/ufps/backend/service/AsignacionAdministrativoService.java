package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.AsignacionAdministrativo;
import co.edu.ufps.backend.repository.AsignacionAdministrativoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsignacionAdministrativoService {
    @Autowired
    private final AsignacionAdministrativoRepository asignacionAdministrativoRepository;

    /**
     * Obtener todas las asignaciones administrativas
     * @return lista de asignaciones administrativas
     */
    public List<AsignacionAdministrativo> getAllAsignaciones() {
        return asignacionAdministrativoRepository.findAll();
    }

    /**
     * Obtener una asignación administrativa por su ID
     * @param id ID de la asignación administrativa
     * @return Asignación administrativa encontrada o un Optional vacío si no existe
     */
    public Optional<AsignacionAdministrativo> getAsignacionById(Long id) {
        return asignacionAdministrativoRepository.findById(id);
    }

    /**
     * Crear una nueva asignación administrativa
     * @param asignacionAdministrativo Objeto de tipo AsignacionAdministrativo
     * @return Asignación administrativa creada
     */
    public AsignacionAdministrativo createAsignacion(AsignacionAdministrativo asignacionAdministrativo) {
        return asignacionAdministrativoRepository.save(asignacionAdministrativo);
    }

    /**
     * Actualizar una asignación administrativa existente
     * @param id ID de la asignación administrativa a actualizar
     * @param asignacionDetails Detalles actualizados de la asignación administrativa
     * @return Asignación administrativa actualizada
     */
    public AsignacionAdministrativo updateAsignacion(Long id, AsignacionAdministrativo asignacionDetails) {
        return asignacionAdministrativoRepository.findById(id).map(asignacion -> {
            asignacion.setPersonal(asignacionDetails.getPersonal());
            asignacion.setAnio(asignacionDetails.getAnio());
            return asignacionAdministrativoRepository.save(asignacion);
        }).orElseThrow(() -> new RuntimeException("Asignación administrativa no encontrada"));
    }

    /**
     * Eliminar una asignación administrativa por su ID
     * @param id ID de la asignación administrativa a eliminar
     */
    public void deleteAsignacion(Long id) {
        asignacionAdministrativoRepository.deleteById(id);
    }
}