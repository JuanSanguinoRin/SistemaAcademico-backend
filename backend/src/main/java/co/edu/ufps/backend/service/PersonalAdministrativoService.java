package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.PersonalAdministrativo;
import co.edu.ufps.backend.repository.PersonalAdministrativoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonalAdministrativoService {

    @Autowired
    private final PersonalAdministrativoRepository personalAdministrativoRepository;

    /**
     * Obtener todos los registros de personal administrativo
     * @return Lista de personal administrativo
     */
    public List<PersonalAdministrativo> getAllPersonalAdministrativo() {
        return personalAdministrativoRepository.findAll();
    }

    /**
     * Obtener un registro de personal administrativo por su ID
     * @param id ID del personal administrativo
     * @return Personal administrativo encontrado o un Optional vacío
     */
    public Optional<PersonalAdministrativo> getPersonalAdministrativoById(Long id) {
        return personalAdministrativoRepository.findById(id);
    }

    /**
     * Crear un nuevo registro de personal administrativo
     * @param personalAdministrativo Objeto PersonalAdministrativo a guardar
     * @return Personal administrativo creado
     */
    public PersonalAdministrativo createPersonalAdministrativo(PersonalAdministrativo personalAdministrativo) {
        return personalAdministrativoRepository.save(personalAdministrativo);
    }

    /**
     * Actualizar un registro existente de personal administrativo
     * @param id ID del registro a actualizar
     * @param personalAdministrativoDetails Nuevos detalles del personal administrativo
     * @return Personal administrativo actualizado
     */
    public PersonalAdministrativo updatePersonalAdministrativo(Long id, PersonalAdministrativo personalAdministrativoDetails) {
        return personalAdministrativoRepository.findById(id).map(personalAdministrativo -> {
            personalAdministrativo.setCargo(personalAdministrativoDetails.getCargo());
            personalAdministrativo.setDepartamento(personalAdministrativoDetails.getDepartamento());
            return personalAdministrativoRepository.save(personalAdministrativo);
        }).orElseThrow(() -> new RuntimeException("Personal Administrativo no encontrado"));
    }

    /**
     * Eliminar un registro de personal administrativo por su ID
     * @param id ID del personal administrativo a eliminar
     */
    public void deletePersonalAdministrativo(Long id) {
        personalAdministrativoRepository.deleteById(id);
    }

    /**
     * Buscar personal administrativo por departamento
     * @param departamento Nombre del departamento
     * @return Lista de personal administrativo del departamento especificado
     */
    public List<PersonalAdministrativo> getPersonalByDepartamento(String departamento) {
        return personalAdministrativoRepository.findAll();
    }
}
