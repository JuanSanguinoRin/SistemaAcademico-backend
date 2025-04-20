package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.HorarioCurso;
import co.edu.ufps.backend.repository.HorarioCursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class HorarioCursoService {

    private final HorarioCursoRepository horarioCursoRepository;

    public List<HorarioCurso> getAllHorarios() {
        return horarioCursoRepository.findAll();
    }

    public Optional<HorarioCurso> getHorarioById(Long id) {
        return horarioCursoRepository.findById(id);
    }

    public HorarioCurso createHorario(HorarioCurso horario) {
        return horarioCursoRepository.save(horario);
    }

    public List<HorarioCurso> getAllHorariosByCurso(Long cursoId) {
        return horarioCursoRepository.findAllByCursoId(cursoId);
    }

    public HorarioCurso updateHorario(Long id, HorarioCurso horarioDetails) {
        HorarioCurso actual = horarioCursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HorarioCurso not found"));

        // Traer todos los horarios actuales, excluyendo el que se está actualizando
        List<HorarioCurso> existentes = horarioCursoRepository.findAll()
                .stream()
                .filter(h -> !h.getId().equals(id))
                .toList();

        for (HorarioCurso existente : existentes) {
            if (seSolapan(horarioDetails, existente)) {
                boolean mismoCurso = existente.getCurso().getId().equals(horarioDetails.getCurso().getId());
                boolean mismaAula = existente.getAula().getId().equals(horarioDetails.getAula().getId());

                if (mismoCurso || mismaAula) {
                    throw new RuntimeException("Conflicto de horario al actualizar: el curso o aula ya están ocupados.");
                }
            }
        }

        // Si no hay conflictos, actualizar
        actual.setDia(horarioDetails.getDia());
        actual.setAula(horarioDetails.getAula());
        actual.setCurso(horarioDetails.getCurso());
        actual.setHoraInicio(horarioDetails.getHoraInicio());
        actual.setHoraFin(horarioDetails.getHoraFin());

        return horarioCursoRepository.save(actual);
    }

    public void deleteHorario(Long id) {
        horarioCursoRepository.deleteById(id);
    }


    //comprobar que el horario no este en conflicto con otro horario o Salon. se debe invocar antes de asignarle horario a un curso o profesor o estudiante
    public boolean seSolapan(HorarioCurso h1, HorarioCurso h2) {
        // Deben ser el mismo día
        if (!h1.getDia().equals(h2.getDia())) return false;

        // Validamos solapamiento en hora (intersección)
        return !h1.getHoraFin().isBefore(h2.getHoraInicio()) &&
                !h1.getHoraInicio().isAfter(h2.getHoraFin());
    }


    public boolean horarioDisponible(HorarioCurso nuevoHorario) {
        List<HorarioCurso> existentes = horarioCursoRepository.findAll();

        for (HorarioCurso existente : existentes) {
            if (seSolapan(nuevoHorario, existente)) {
                // Verificar conflicto por curso o por aula
                boolean mismoCurso = existente.getCurso().getId().equals(nuevoHorario.getCurso().getId());
                boolean mismaAula = existente.getAula().getId().equals(nuevoHorario.getAula().getId());

                if (mismoCurso || mismaAula) {
                    return false; // Hay conflicto
                }
            }
        }

        return true; // No hay conflictos
    }
}

