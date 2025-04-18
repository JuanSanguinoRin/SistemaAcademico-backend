package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Calificacion;
import co.edu.ufps.backend.model.Curso;
import co.edu.ufps.backend.model.Docente;
import co.edu.ufps.backend.model.HorarioCurso;
import co.edu.ufps.backend.service.HorarioCursoService;

import co.edu.ufps.backend.repository.DocenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocenteService {

    @Autowired
    private final DocenteRepository docenteRepository;
    private final CursoService cursoService;
    private final HorarioCursoService horarioCursoService;
    private final CalificacionService calificacionService;

    public List<Docente> getAllDocentes() {
        return docenteRepository.findAll();
    }

    // Obtener un docente por su código
    public Optional<Docente> getDocenteByCodigo(Long codigoDocente) {
        return docenteRepository.findById(codigoDocente);
    }

    // Crear un nuevo docente
    public Docente createDocente(Docente docente) {
        return docenteRepository.save(docente);
    }

    // Cambiar algunos datos del docente (como experiencia o datos de la persona)
    public Docente cambiarDatos(Long codigoDocente, Docente nuevosDatos) {
        return docenteRepository.findById(codigoDocente).map(docente -> {
            if (nuevosDatos.getExperiencia() != null) {
                docente.setExperiencia(nuevosDatos.getExperiencia());
            }

            if (nuevosDatos.getPersona() != null) {
                if (nuevosDatos.getPersona().getCorreoElectronico() != null) {
                    String nuevoCorreo = nuevosDatos.getPersona().getCorreoElectronico();
                    if (!nuevoCorreo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                        throw new IllegalArgumentException("Correo electrónico inválido");
                    }
                    docente.getPersona().setCorreoElectronico(nuevoCorreo);
                }

                if (nuevosDatos.getPersona().getDireccion() != null) {
                    docente.getPersona().setDireccion(nuevosDatos.getPersona().getDireccion());
                }

                if (nuevosDatos.getPersona().getTelefono() != null) {
                    docente.getPersona().setTelefono(nuevosDatos.getPersona().getTelefono());
                }

                // Aquí podrías guardar la entidad `Persona` en su repositorio si tienes uno.
            }

            return docenteRepository.save(docente);
        }).orElseThrow(() -> new RuntimeException("Docente no encontrado"));
    }

// FALTAN LOS METODOS CNSULTAR HORARIO, CURSO, NOTAS, DEFINIR HORARIO
    // Eliminar un docente por su código
    public void deleteDocente(Long codigoDocente) {
        docenteRepository.deleteById(codigoDocente);
    }

    // 1. Consultar horarios del docente
    public List<HorarioCurso> getHorariosByDocente(Long docenteId) {
        List<Curso> cursos = cursoService.getCursosByDocente(docenteId);
        return cursos.stream()
                .flatMap(curso -> horarioCursoService.getAllHorarios().stream()
                        .filter(horario -> horario.getCurso().getId().equals(curso.getId())))
                .toList();
    }

    // 2. Consultar cursos del docente
    public List<Curso> getCursosDelDocente(Long docenteId) {
        return cursoService.getCursosByDocente(docenteId);
    }

    // 3. Consultar calificaciones (notas) de los estudiantes en cursos del docente
    public List<Calificacion> getNotasDeCursosDocente(Long docenteId) {
        List<Curso> cursos = cursoService.getCursosByDocente(docenteId);
        List<Long> idsCursos = cursos.stream().map(Curso::getId).toList();

        return calificacionService.getAllCalificaciones().stream()
                .filter(calificacion -> {
                    if (calificacion.getEstudianteCurso() == null ||
                            calificacion.getEstudianteCurso().getCurso() == null) {
                        return false;
                    }
                    Long cursoId = calificacion.getEstudianteCurso().getCurso().getId();
                    return idsCursos.contains(cursoId);
                })
                .toList();
    }

    // 4. Definir (crear) un horario para un curso
    public HorarioCurso definirHorarioCurso(HorarioCurso horarioCurso) {
        return horarioCursoService.createHorario(horarioCurso);
    }

    // 5. Modificar un horario existente
    public HorarioCurso modificarHorarioCurso(Long idHorario, HorarioCurso datosActualizados) {
        return horarioCursoService.updateHorario(idHorario, datosActualizados);
    }

}

