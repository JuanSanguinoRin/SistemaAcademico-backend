package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.*;
import co.edu.ufps.backend.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CursoService {
    private final CursoRepository cursoRepository;

    public List<Curso> getAllCursos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> getCursoById(Long id) {
        return cursoRepository.findById(id);
    }

    public List<Curso> getCursosByPrograma(Long programaId) {
        return cursoRepository.findByProgramaId(programaId);
    }

    public List<Curso> getCursosBySemestre(Long semestreId) {
        return cursoRepository.findBySemestreId(semestreId);
    }



    public List<Curso> getCursosVacacionales(Boolean vacacional) {
        return cursoRepository.findByVacacional(vacacional);
    }

    // En CursoService.java
    public Optional<Asignatura> getAsignaturaByCurso(Long cursoId) {
        Optional<Curso> curso = cursoRepository.findById(cursoId);
        return curso.map(Curso::getAsignatura);
    }

    public Curso createCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Curso updateCurso(Long id, Curso cursoDetails) {
        return cursoRepository.findById(id).map(curso -> {
            curso.setNombre(cursoDetails.getNombre());
            curso.setDescripcion(cursoDetails.getDescripcion());
            curso.setContenido(cursoDetails.getContenido());
            curso.setObjetivos(cursoDetails.getObjetivos());
            curso.setCompetencias(cursoDetails.getCompetencias());
            curso.setCupoMaximo(cursoDetails.getCupoMaximo());
            curso.setAsignatura(cursoDetails.getAsignatura());
            curso.setPrograma(cursoDetails.getPrograma());
            curso.setSemestre(cursoDetails.getSemestre());
            curso.setGrupo(cursoDetails.getGrupo());
            curso.setVacacional(cursoDetails.getVacacional());
            return cursoRepository.save(curso);
        }).orElseThrow(() -> new RuntimeException("Curso not found"));
    }

    public void deleteCurso(Long id) {
        cursoRepository.deleteById(id);
    }

    public Curso obtenerDetalles(Long cursoId) {
        return cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso not found"));
    }

}