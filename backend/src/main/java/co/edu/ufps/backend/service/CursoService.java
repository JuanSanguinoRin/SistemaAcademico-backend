package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Curso;
import co.edu.ufps.backend.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CursoService {

    @Autowired
    private final CursoRepository cursoRepository;

    // Obtener todos los cursos
    public List<Curso> getAllCursos() {
        return cursoRepository.findAll();
    }

    // Obtener un curso por su código
    public Optional<Curso> getCursoByCodigo(Long codigo) {
        return cursoRepository.findById(codigo);
    }

    // Crear un nuevo curso
    public Curso createCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    // Actualizar un curso existente
    public Curso updateCurso(Long codigo, Curso cursoDetails) {
        return cursoRepository.findById(codigo).map(curso -> {
            curso.setNombre(cursoDetails.getNombre());
            curso.setDescripcion(cursoDetails.getDescripcion());
            curso.setContenido(cursoDetails.getContenido());
            curso.setDocente(cursoDetails.getDocente());
            curso.setObjetivos(cursoDetails.getObjetivos());
            curso.setCompetencias(cursoDetails.getCompetencias());
            curso.setCupoMaximo(cursoDetails.getCupoMaximo());
            curso.setAsignatura(cursoDetails.getAsignatura());
            curso.setPrograma(cursoDetails.getPrograma());
            curso.setSemestre(cursoDetails.getSemestre());
            curso.setGrupo(cursoDetails.getGrupo());
            curso.setVacacional(cursoDetails.getVacacional());
            return cursoRepository.save(curso);
        }).orElseThrow(() -> new RuntimeException("Curso no encontrado"));
    }

    // Eliminar un curso por su código
    public void deleteCurso(Long codigo) {
        cursoRepository.deleteById(codigo);
    }
}