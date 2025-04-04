package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Estudiante;
import co.edu.ufps.backend.model.Semestre;
import co.edu.ufps.backend.repository.EstudianteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstudianteService {
    private final EstudianteRepository estudianteRepository;

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

    public Integer calcularSemestre(Long estudianteId) {

        return 0; // Placeholder
    }

    public void actualizarHistorialAcademico(Long estudianteId, Semestre semestre) {

    }
}