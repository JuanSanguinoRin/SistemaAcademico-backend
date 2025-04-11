package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.HorarioCurso;
import co.edu.ufps.backend.repository.HorarioCursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HorarioCursoService {
    @Autowired
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

    public HorarioCurso updateHorario(Long id, HorarioCurso horarioDetails) {
        return horarioCursoRepository.findById(id)
                .map(horario -> {
                    horario.setDia(horarioDetails.getDia());
                    horario.setHoraInicio(horarioDetails.getHoraInicio());
                    horario.setHoraFin(horarioDetails.getHoraFin());
                    horario.setAula(horarioDetails.getAula());
                    horario.setCurso(horarioDetails.getCurso());
                    return horarioCursoRepository.save(horario);
                })
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
    }

    public void deleteHorario(Long id) {
        HorarioCurso horario = horarioCursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        horarioCursoRepository.delete(horario);
    }



}

