package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.HorarioAdministrativo;
import co.edu.ufps.backend.repository.HorarioAdministrativoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioAdministrativoService {

    private final HorarioAdministrativoRepository horarioAdministrativoRepository;

    @Autowired
    public HorarioAdministrativoService(HorarioAdministrativoRepository horarioAdministrativoRepository) {
        this.horarioAdministrativoRepository = horarioAdministrativoRepository;
    }

    /**
     * Devuelve la lista completa de horarios administrativos.
     *
     * @return lista de todos los horarios administrativos
     */
    public List<HorarioAdministrativo> listarTodos() {
        return horarioAdministrativoRepository.findAll();
    }

    /**
     * Busca un horario administrativo por su ID.
     *
     * @param id identificador del horario
     * @return horario administrativo si existe, de lo contrario Optional vacío
     */
    public Optional<HorarioAdministrativo> buscarPorId(Long id) {
        return horarioAdministrativoRepository.findById(id);
    }

    /**
     * Guarda o actualiza un horario administrativo.
     *
     * @param horario horario administrativo a guardar
     * @return horario administrativo guardado
     */
    public HorarioAdministrativo guardar(HorarioAdministrativo horario) {
        return horarioAdministrativoRepository.save(horario);
    }

    /**
     * Elimina un horario administrativo por su ID.
     *
     * @param id identificador del horario a eliminar
     */
    public void eliminar(Long id) {
        horarioAdministrativoRepository.deleteById(id);
    }
}