package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Privilegio;
import co.edu.ufps.backend.repository.PrivilegioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrivilegioService {

    @Autowired
    private final PrivilegioRepository privilegioRepository;

    /**
     * Obtener todos los privilegios
     * @return Lista de privilegios
     */
    public List<Privilegio> getAllPrivilegios() {
        return privilegioRepository.findAll();
    }

    /**
     * Obtener un privilegio por su ID
     * @param id ID del privilegio
     * @return Privilegio encontrado o un Optional vacío
     */
    public Optional<Privilegio> getPrivilegioById(Long id) {
        return privilegioRepository.findById(id);
    }

    /**
     * Crear un nuevo privilegio
     * @param privilegio Objeto Privilegio a guardar
     * @return Privilegio creado
     */
    public Privilegio createPrivilegio(Privilegio privilegio) {
        return privilegioRepository.save(privilegio);
    }

    /**
     * Actualizar un privilegio existente
     * @param id ID del privilegio a actualizar
     * @param privilegioDetails Nuevos detalles del privilegio
     * @return Privilegio actualizado
     */
    public Privilegio updatePrivilegio(Long id, Privilegio privilegioDetails) {
        return privilegioRepository.findById(id).map(privilegio -> {
            privilegio.setNombre(privilegioDetails.getNombre());
            privilegio.setDescripcion(privilegioDetails.getDescripcion());
            privilegio.setRol(privilegioDetails.getRol());
            return privilegioRepository.save(privilegio);
        }).orElseThrow(() -> new RuntimeException("Privilegio no encontrado"));
    }

    /**
     * Eliminar un privilegio por su ID
     * @param id ID del privilegio a eliminar
     */
    public void deletePrivilegio(Long id) {
        privilegioRepository.deleteById(id);
    }

    /**
     * Buscar privilegios por ID de rol
     * @param rolId ID del rol
     * @return Lista de privilegios asociados al rol especificado
     */
    public List<Privilegio> getPrivilegiosByRolId(Long rolId) {
        return privilegioRepository.findAll();
    }
}
