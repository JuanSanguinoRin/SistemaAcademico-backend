package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.*;
import co.edu.ufps.backend.repository.EstudianteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstudianteService {
    private final EstudianteRepository estudianteRepository;
    //private final EstudianteCursoService estudianteCursoService;
    //private final HistorialAcademicoService historialAcademicoService;

    public List<Estudiante> getAllEstudiantes() {
        return estudianteRepository.findAll();
    }

    public Optional<Estudiante> getEstudianteById(Long id) {
        return estudianteRepository.findById(id);
    }

    public List<Estudiante> getEstudiantesByPrograma(Long programaId) {
        return estudianteRepository.findByProgramaId(programaId);
    }

    public Optional<Estudiante> getEstudianteByPersonaId(Long personaId) {
        return estudianteRepository.findByPersonaId(personaId);
    }

    public Estudiante createEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public Estudiante updateEstudiante(Long id, Estudiante estudianteDetails) {
        return estudianteRepository.findById(id).map(estudiante -> {
            estudiante.setPrograma(estudianteDetails.getPrograma());
            estudiante.setCreditosAprobados(estudianteDetails.getCreditosAprobados());
            estudiante.setPromedioPonderado(estudianteDetails.getPromedioPonderado());
            estudiante.setPersona(estudianteDetails.getPersona());
            return estudianteRepository.save(estudiante);
        }).orElseThrow(() -> new RuntimeException("Estudiante not found"));
    }

    public void deleteEstudiante(Long id) {
        estudianteRepository.deleteById(id);
    }

    public Float calcularPonderado(Long estudianteId) {
        // Lógica para calcular el promedio ponderado
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante not found"));
        // Aquí iría la lógica para calcular el promedio
        return estudiante.getPromedioPonderado();
    }

    //meteer en el controller
    /*public int calcularSemestre(Long estudianteId) {
        // 1. Obtener todos los cursos del estudiante
        List<EstudianteCurso> cursosEstudiante = estudianteCursoService.getEstudianteCursosByEstudiante(estudianteId);

        // 2. Filtrar por cursos aprobados
        List<Integer> semestresAprobados = cursosEstudiante.stream()
                .filter(ec -> "Aprobado".equalsIgnoreCase(ec.getEstado()))
                .map(ec -> {
                    Curso curso = ec.getCurso();
                    if (curso != null && curso.getAsignatura() != null) {
                        return curso.getAsignatura().getSemestre();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();

        // 3. Retornar el semestre más bajo aprobado o lanzar excepción si no hay
        return semestresAprobados.stream()
                .min(Integer::compareTo)
                .orElseThrow(() -> new RuntimeException("El estudiante no ha aprobado asignaturas con semestre definido."));
    }*/

    public void actualizarHistorialAcademico(Long id, Semestre semestre) {
    }
}