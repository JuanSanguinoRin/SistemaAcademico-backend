package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.*;
import co.edu.ufps.backend.repository.CursoRepository;
import co.edu.ufps.backend.repository.EstudianteCursoRepository;
import co.edu.ufps.backend.repository.PensumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CursoService {
    private final CursoRepository cursoRepository;
    private final EstudianteService estudianteService;
    private final AsignaturaPrerrequisitoService asignaturaPrerrequisitoService;
    private final EstudianteCursoRepository estudianteCursoRepository;
    private final PensumRepository pensumRepository;

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

    public List<Curso> getCursosMatriculables(Long estudianteId) {
        try {
            log.info("Iniciando búsqueda de cursos matriculables para estudiante ID: {}", estudianteId);

            // Validación de entrada
            if (estudianteId == null || estudianteId <= 0) {
                log.error("ID de estudiante inválido: {}", estudianteId);
                throw new IllegalArgumentException("ID de estudiante inválido");
            }

            // 1. Obtener información del estudiante
            Estudiante estudiante = estudianteService.getEstudianteById(estudianteId)
                    .orElseThrow(() -> {
                        log.error("Estudiante no encontrado con ID: {}", estudianteId);
                        return new RuntimeException("Estudiante no encontrado con ID: " + estudianteId);
                    });

            if (estudiante.getPrograma() == null) {
                log.error("El estudiante {} no tiene programa asociado", estudianteId);
                throw new RuntimeException("El estudiante no tiene un programa asociado.");
            }

            Long programaIdDelEstudiante = estudiante.getPrograma().getId();
            log.info("Estudiante pertenece al programa ID: {}", programaIdDelEstudiante);

            // Obtener el Pensum asociado al Programa del estudiante
            Pensum pensumEstudiante = pensumRepository.findByPrograma_Id(programaIdDelEstudiante)
                    .orElseThrow(() -> {
                        log.error("No se encontró pensum para programa ID: {}", programaIdDelEstudiante);
                        return new RuntimeException("No se encontró un pensum para el programa del estudiante: " + estudiante.getPrograma().getNombre());
                    });

            Long pensumIdDelEstudianteActual = pensumEstudiante.getId();
            log.info("Pensum del estudiante ID: {}", pensumIdDelEstudianteActual);

            // 2. Obtener todos los cursos ofertados del programa del estudiante
            List<Curso> cursosOfertados = cursoRepository.findByProgramaId(programaIdDelEstudiante);
            log.info("Cursos ofertados encontrados: {}", cursosOfertados.size());

            // 3. Obtener IDs de asignaturas aprobadas, cursando y canceladas por el estudiante
            List<Long> idsAsignaturasAprobadas = getAsignaturasAprobadasPorEstudiante(estudianteId);
            List<Long> idsAsignaturasCursando = getAsignaturasCursandoPorEstudiante(estudianteId);
            List<Long> idsAsignaturasCanceladas = getAsignaturasCanceladasPorEstudiante(estudianteId);

            log.info("Asignaturas aprobadas: {}, Asignaturas cursando: {}, Asignaturas canceladas: {}",
                    idsAsignaturasAprobadas.size(), idsAsignaturasCursando.size(), idsAsignaturasCanceladas.size());

            List<Curso> cursosMatriculables = new ArrayList<>();

            // 4. Filtrar cursos
            for (Curso cursoPotencial : cursosOfertados) {
                try {
                    if (esCursoMatriculable(cursoPotencial, pensumIdDelEstudianteActual,
                            idsAsignaturasAprobadas, idsAsignaturasCursando, idsAsignaturasCanceladas)) {
                        cursosMatriculables.add(cursoPotencial);
                    }
                } catch (Exception e) {
                    log.warn("Error al evaluar curso ID {}: {}", cursoPotencial.getId(), e.getMessage());
                }
            }

            log.info("Cursos matriculables encontrados: {}", cursosMatriculables.size());
            return cursosMatriculables;

        } catch (Exception e) {
            log.error("Error al obtener cursos matriculables para estudiante {}: {}", estudianteId, e.getMessage(), e);
            throw new RuntimeException("Error al obtener cursos matriculables: " + e.getMessage());
        }
    }

    private boolean esCursoMatriculable(Curso curso, Long pensumIdEstudiante,
                                        List<Long> asignaturasAprobadas, List<Long> asignaturasCursando,
                                        List<Long> asignaturasCanceladas) {

        Asignatura asignaturaDelCurso = curso.getAsignatura();

        if (asignaturaDelCurso == null || asignaturaDelCurso.getPensum() == null) {
            log.debug("Curso {} no tiene asignatura o pensum asociado", curso.getId());
            return false;
        }

        // Filtro a: La asignatura debe pertenecer al pensum del estudiante
        if (!asignaturaDelCurso.getPensum().getId().equals(pensumIdEstudiante)) {
            log.debug("Asignatura {} no pertenece al pensum del estudiante", asignaturaDelCurso.getId());
            return false;
        }

        // Filtro b: El estudiante no debe haber aprobado, estar cursando, o haber cancelado la asignatura
        if (asignaturasAprobadas.contains(asignaturaDelCurso.getId()) ||
                asignaturasCursando.contains(asignaturaDelCurso.getId()) ||
                asignaturasCanceladas.contains(asignaturaDelCurso.getId())) {
            log.debug("Estudiante ya aprobó, está cursando o canceló la asignatura {}", asignaturaDelCurso.getId());
            return false;
        }

        // Filtro c: Verificar prerrequisitos
        if (!cumplePrerrequisitos(asignaturaDelCurso.getId(), asignaturasAprobadas)) {
            log.debug("No cumple prerrequisitos para asignatura {}", asignaturaDelCurso.getId());
            return false;
        }

        // Filtro d: Verificar cupos
        if (!tieneCapacidadDisponible(curso)) {
            log.debug("No hay cupos disponibles para curso {}", curso.getId());
            return false;
        }

        return true;
    }

    private List<Long> getAsignaturasAprobadasPorEstudiante(Long estudianteId) {
        try {
            // Implementar consulta personalizada o usar metodo existente
            return estudianteCursoRepository.findAsignaturaIdsByEstudianteIdAndEstado(estudianteId, "Aprobado");
        } catch (Exception e) {
            log.warn("Error al obtener asignaturas aprobadas para estudiante {}: {}", estudianteId, e.getMessage());
            return new ArrayList<>();
        }
    }


    private List<Long> getAsignaturasCanceladasPorEstudiante(Long estudianteId) {
        try {
            // Obtener las asignaturas que el estudiante ha cancelado
            return estudianteCursoRepository.findAsignaturaIdsByEstudianteIdAndEstado(estudianteId, "Cancelado");
        } catch (Exception e) {
            log.warn("Error al obtener asignaturas canceladas para estudiante {}: {}", estudianteId, e.getMessage());
            return new ArrayList<>();
        }
    }


    private List<Long> getAsignaturasCursandoPorEstudiante(Long estudianteId) {
        try {
            // Implementar consulta personalizada o usar metodo existente
            return estudianteCursoRepository.findAsignaturaIdsByEstudianteIdAndEstado(estudianteId, "Cursando");
        } catch (Exception e) {
            log.warn("Error al obtener asignaturas cursando para estudiante {}: {}", estudianteId, e.getMessage());
            return new ArrayList<>();
        }
    }

    private boolean cumplePrerrequisitos(Long asignaturaId, List<Long> asignaturasAprobadas) {
        try {
            List<AsignaturaPrerrequisito> prerrequisitos =
                    asignaturaPrerrequisitoService.getAllPrerrequisitosByAsignaturaId(asignaturaId);

            if (prerrequisitos.isEmpty()) {
                return true; // No tiene prerrequisitos
            }

            for (AsignaturaPrerrequisito prerreq : prerrequisitos) {
                if (prerreq.getPrerrequisito() == null ||
                        !asignaturasAprobadas.contains(prerreq.getPrerrequisito().getId())) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            log.warn("Error al verificar prerrequisitos para asignatura {}: {}", asignaturaId, e.getMessage());
            return false;
        }
    }

    private boolean tieneCapacidadDisponible(Curso curso) {
        try {
            long cuposOcupados = estudianteCursoRepository.countByCurso_IdAndEstadoIgnoreCase(
                    curso.getId(), "Cursando");
            return cuposOcupados < curso.getCupoMaximo();
        } catch (Exception e) {
            log.warn("Error al verificar cupos para curso {}: {}", curso.getId(), e.getMessage());
            return false;
        }
    }
}