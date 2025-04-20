package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.AsignacionAdministrativo;
import co.edu.ufps.backend.model.HorarioAdministrativo;
import co.edu.ufps.backend.repository.HorarioAdministrativoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Date;
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


    // Métodos de la clase
    public HorarioAdministrativo generarHorario(String dia, String departamento, LocalTime horaInicio, LocalTime horaFin, AsignacionAdministrativo asignacion) {
        if (asignacion == null || asignacion.getPersonal() == null) {
            throw new IllegalArgumentException("La asignación del empleado no puede ser nula.");
        }

        // Crear y configurar el nuevo horario administrativo
        HorarioAdministrativo horario = new HorarioAdministrativo();
        horario.setDia(dia);
        horario.setDepartamento(departamento);
        horario.setHoraInicio(horaInicio);
        horario.setHoraFin(horaFin);
        horario.setAsignacionAdministrativo(asignacion);

        // Retornar el objeto creado
        return horario;
    }


}