package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.HorarioCurso;
import co.edu.ufps.backend.repository.HorarioCursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
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

    public boolean hayConflictoHorario(HorarioCurso h1, HorarioCurso h2) {

        if (!h1.getDia().equals(h2.getDia())) {
            return false;
        }

        LocalTime inicio1 = h1.getHoraInicio();
        LocalTime fin1 = h1.getHoraFin();
        LocalTime inicio2 = h2.getHoraInicio();
        LocalTime fin2 = h2.getHoraFin();

        return (inicio1.isBefore(fin2) && inicio2.isBefore(fin1));
    }


    public boolean fechaCoincideConHorario(Date fecha, HorarioCurso horario) {
        // Obtener día de la semana en texto desde la fecha
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);

        int diaSemana = calendar.get(Calendar.DAY_OF_WEEK); // 1=Domingo, 2=Lunes, ..., 7=Sábado
        String diaTexto = mapearDiaSemana(diaSemana); // Método auxiliar

        // Validar día
        if (!horario.getDia().equalsIgnoreCase(diaTexto)) {
            return false;
        }

        // Obtener hora y minutos
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minutos = calendar.get(Calendar.MINUTE);
        LocalTime horaAsistencia = LocalTime.of(hora, minutos);

        // Validar si la hora está dentro del rango del horario
        return !horaAsistencia.isBefore(horario.getHoraInicio()) &&
                !horaAsistencia.isAfter(horario.getHoraFin());
    }

    private String mapearDiaSemana(int diaSemana) {
        return switch (diaSemana) {
            case Calendar.MONDAY    -> "LUNES";
            case Calendar.TUESDAY   -> "MARTES";
            case Calendar.WEDNESDAY -> "MIERCOLES";
            case Calendar.THURSDAY  -> "JUEVES";
            case Calendar.FRIDAY    -> "VIERNES";
            case Calendar.SATURDAY  -> "SABADO";
            case Calendar.SUNDAY    -> "DOMINGO";
            default -> throw new IllegalArgumentException("Día inválido");
        };
    }

}

