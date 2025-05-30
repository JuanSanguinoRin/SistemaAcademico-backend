package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.*;
import co.edu.ufps.backend.repository.AsistenciaRepository;
import co.edu.ufps.backend.repository.EstudianteCursoRepository;
import co.edu.ufps.backend.repository.CalificacionRepository;
import co.edu.ufps.backend.repository.EstudianteRepository;
import co.edu.ufps.backend.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstudianteCursoService {
    private final EstudianteCursoRepository estudianteCursoRepository;
    private final AsistenciaService asistenciaService;
    private final EstudianteService estudianteService;
    private final CursoService cursoService;
    private final AsignaturaPrerrequisitoService asignaturaPrerrequisitoService;
    private final AsignaturaService asignaturaService;
    private final HorarioCursoService horarioCursoService;

    public List<EstudianteCurso> getAllEstudianteCursos() {
        return estudianteCursoRepository.findAll();
    }

    public Optional<EstudianteCurso> getEstudianteCursoById(Long id) {
        return estudianteCursoRepository.findById(id);
    }
    //es lo mismo que getEstudianteCursoById pero sirve para suponer que siempre se encuentr el id, se usara solo en el backend
    public EstudianteCurso getById(Long id) {
        return estudianteCursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación Estudiante-Curso no encontrada"));
    }


    public List<EstudianteCurso> getEstudianteCursosByEstudiante(Long estudianteId) {
        return estudianteCursoRepository.findByEstudianteId(estudianteId);
    }

    public List<EstudianteCurso> getEstudianteCursosByCurso(Long cursoId) {
        return estudianteCursoRepository.findByCursoId(cursoId);
    }

    public Optional<EstudianteCurso> getEstudianteCursoByEstudianteAndCurso(Long estudianteId, Long cursoId) {
        return estudianteCursoRepository.findByEstudianteCodigoEstudianteAndCursoId(estudianteId, cursoId);
    }

    public List<EstudianteCurso> getEstudianteCursosByEstado(String estado) {
        return estudianteCursoRepository.findByEstado(estado);
    }
    //no llamar
    public EstudianteCurso createEstudianteCurso(EstudianteCurso estudianteCurso) {
        return estudianteCursoRepository.save(estudianteCurso);
    }

    public EstudianteCurso updateEstudianteCurso(Long id, EstudianteCurso estudianteCursoDetails) {
        return estudianteCursoRepository.findById(id).map(estudianteCurso -> {
            // pasar de Cursando a cancelado
            estudianteCurso.setEstado("Cancelado");
            return estudianteCursoRepository.save(estudianteCurso);
        }).orElseThrow(() -> new RuntimeException("EstudianteCurso not found"));
    }

    public void deleteEstudianteCurso(Long id) {
        estudianteCursoRepository.deleteById(id);
    }
    public EstudianteCurso getInscripcion(Long cursoId, Long estudianteId) {
        return estudianteCursoRepository
                .findByCursoIdAndEstudianteCodigoEstudiante(cursoId, estudianteId)
                .orElseThrow(() -> new RuntimeException("El estudiante no está inscrito en este curso"));
    }


    public Boolean comprobarRehabilitacion(Long estudianteCursoId) {
        EstudianteCurso ec = estudianteCursoRepository.findById(estudianteCursoId)
                .orElseThrow(() -> new RuntimeException("EstudianteCurso no encontrado"));

        return Boolean.TRUE.equals(ec.getHabilitacion());
    }

    public void cancelar(Long estudianteCursoId) {
        // Lógica para cancelar curso
        EstudianteCurso estudianteCurso = estudianteCursoRepository.findById(estudianteCursoId)
                .orElseThrow(() -> new RuntimeException("EstudianteCurso not found"));
        estudianteCurso.setEstado("Cancelado");
        estudianteCursoRepository.save(estudianteCurso);
    }

    public EstudianteCurso matricularCurso(Long estudianteId, Long cursoId) {
        Estudiante estudiante = estudianteService.getEstudianteById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Curso curso = cursoService.getCursoById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Validar si ya está matriculado
        Optional<EstudianteCurso> yaMatriculado = estudianteCursoRepository.findByCursoIdAndEstudianteId(cursoId, estudianteId);
        if (yaMatriculado.isPresent()) {
            throw new RuntimeException("El estudiante ya está matriculado en este curso.");
        }

        List<EstudianteCurso> estudiantesMatriculados = this.getEstudianteCursosByCurso(cursoId);

        int cuposOcupados = estudiantesMatriculados.size();

        if (cuposOcupados >= curso.getCupoMaximo()) {
            throw new RuntimeException("No hay cupos disponibles en este curso.");
        }

        Optional<Asignatura> asignaturaOpt = cursoService.getAsignaturaByCurso(cursoId);
        if (!asignaturaOpt.isPresent()) {
            throw new RuntimeException("El curso no tiene una asignatura asociada.");
        }

        Asignatura asignatura = asignaturaOpt.get();

        // Validar prerrequisitos
        validarPrerrequisitos(estudianteId, asignatura);

        // Validar horarios
        validarHorarios(estudianteId, cursoId);

        EstudianteCurso inscripcion = new EstudianteCurso();
        inscripcion.setEstudiante(estudiante);
        inscripcion.setCurso(curso);
        inscripcion.setEstado("Cursando");
        inscripcion.setHabilitacion(false);

        return estudianteCursoRepository.save(inscripcion);
    }

    private void validarPrerrequisitos(Long estudianteId, Asignatura asignatura) {
        System.out.println("Validando prerrequisitos para la asignatura: " + asignatura.getNombre() + " (ID: " + asignatura.getId() + ")");

        // Obtener prerrequisitos de la asignatura
        List<AsignaturaPrerrequisito> prerrequisitosAsignatura = asignaturaPrerrequisitoService.getAllPrerrequisitosByAsignaturaId(asignatura.getId());

        System.out.println("Prerrequisitos encontrados: " + prerrequisitosAsignatura.size());
        prerrequisitosAsignatura.forEach(p ->
                System.out.println("- " + p.getPrerrequisito().getNombre() + " (ID: " + p.getPrerrequisito().getId() + ")"));

        if (prerrequisitosAsignatura.isEmpty()) {
            System.out.println("No hay prerrequisitos para esta asignatura");
            return;
        }

        // Obtener cursos aprobados del estudiante
        List<EstudianteCurso> cursosAprobados = this.getCursosAprobadosByEstudiante(estudianteId);

        System.out.println("Cursos aprobados por el estudiante: " + cursosAprobados.size());
        cursosAprobados.forEach(ca -> {
            if (ca.getCurso() != null && ca.getCurso().getAsignatura() != null) {
                System.out.println("- Curso: " + ca.getCurso().getNombre() +
                        ", Asignatura: " + ca.getCurso().getAsignatura().getNombre() +
                        " (ID: " + ca.getCurso().getAsignatura().getId() + ")");
            } else {
                System.out.println("- Curso sin asignatura asociada: " + ca.getId());
            }
        });

        // Crear una lista de IDs de asignaturas aprobadas para facilitar la verificación
        List<Long> asignaturasAprobadasIds = new ArrayList<>();
        for (EstudianteCurso ec : cursosAprobados) {
            if (ec.getCurso() != null && ec.getCurso().getAsignatura() != null) {
                asignaturasAprobadasIds.add(ec.getCurso().getAsignatura().getId());
            }
        }

        System.out.println("IDs de asignaturas aprobadas: " + asignaturasAprobadasIds);

        // Verificar que todos los prerrequisitos estén aprobados
        List<Asignatura> prerrequisitosNoAprobados = new ArrayList<>();

        for (AsignaturaPrerrequisito prerreq : prerrequisitosAsignatura) {
            Long idPrerrequisito = prerreq.getPrerrequisito().getId();
            System.out.println("Verificando prerrequisito ID: " + idPrerrequisito);

            if (!asignaturasAprobadasIds.contains(idPrerrequisito)) {
                System.out.println("¡NO APROBADO!");
                prerrequisitosNoAprobados.add(prerreq.getPrerrequisito());
            } else {
                System.out.println("APROBADO");
            }
        }

        if (!prerrequisitosNoAprobados.isEmpty()) {
            StringBuilder mensaje = new StringBuilder("El estudiante no cumple con los siguientes prerrequisitos: ");
            prerrequisitosNoAprobados.forEach(a -> mensaje.append(a.getNombre()).append(", "));
            mensaje.delete(mensaje.length() - 2, mensaje.length());

            System.out.println("Error de prerrequisitos: " + mensaje);
            throw new RuntimeException(mensaje.toString());
        }

        System.out.println("Todos los prerrequisitos verificados correctamente");
    }

    private void validarHorarios(Long estudianteId, Long cursoId) {
        try{

            Curso cursoAMatricular = cursoService.getCursoById(cursoId)
                    .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

            List<HorarioCurso> horariosNuevoCurso = horarioCursoService.getAllHorariosByCurso(cursoId);

            if (horariosNuevoCurso.isEmpty()) {
                throw new RuntimeException("El curso no tiene horarios definidos.");
            }

            // Obtener cursos actuales del estudiante
            List<EstudianteCurso> cursosActuales = this.getCursosActualesByEstudiante(estudianteId);

            System.out.println("Cursos actuales: " + cursosActuales.size());

            // Para cada curso del estudiante, verificar si hay conflicto de horarios
            for (EstudianteCurso ec : cursosActuales) {
                List<HorarioCurso> horariosCursoActual = horarioCursoService.getAllHorariosByCurso(ec.getCurso().getId());

                for (HorarioCurso horarioActual : horariosCursoActual) {
                    for (HorarioCurso horarioNuevo : horariosNuevoCurso) {
                        if (horarioCursoService.hayConflictoHorario(horarioActual, horarioNuevo)) {
                            throw new RuntimeException(
                                    "Conflicto de horario con el curso " + ec.getCurso().getNombre() +
                                            ". Día: " + horarioNuevo.getDia() +
                                            ", Hora: " + horarioNuevo.getHoraInicio() + " - " + horarioNuevo.getHoraFin());
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al validar horarios: " + e.getMessage(), e);
        }
    }



    // Metodo para obtener cursos aprobados de un estudiante específico
    public List<EstudianteCurso> getCursosAprobadosByEstudiante(Long estudianteId) {
        // Asegúrate de que la consulta es case-insensitive o coincide exactamente con cómo
        // se almacena el estado "Aprobado" en la base de datos
        List<EstudianteCurso> cursosAprobados = estudianteCursoRepository.findByEstudianteIdAndEstado(estudianteId, "Aprobado");

        System.out.println("Buscando cursos aprobados del estudiante ID: " + estudianteId);
        System.out.println("Encontrados: " + cursosAprobados.size());

        return cursosAprobados;
    }

    public List<EstudianteCurso> getCursosActualesByEstudiante(Long estudianteId)
    {

        List<EstudianteCurso> cursosActuales = estudianteCursoRepository.findByEstudianteIdAndEstado(estudianteId, "Cursando");

        return cursosActuales;

    }

    public List<EstudianteCurso> getCursosReprobadosByEstudiante(Long estudianteId)
    {

        List<EstudianteCurso> cursosAprobados = estudianteCursoRepository.findByEstudianteIdAndEstado(estudianteId, "Reprobado");

        return cursosAprobados;

    }

    public List<HorarioCurso> getHorarioCompletoEstudianteActual(Long estudianteId) {
        List<EstudianteCurso> cursosActuales = this.getCursosActualesByEstudiante(estudianteId);
        List<HorarioCurso> horarioCompleto = new ArrayList<>();

        if (cursosActuales != null) {
            for (EstudianteCurso ec : cursosActuales) {
                if (ec.getCurso() != null && ec.getCurso().getId() != null) {
                    List<HorarioCurso> horariosDelCurso = horarioCursoService.getAllHorariosByCurso(ec.getCurso().getId()); //
                    if (horariosDelCurso != null) {
                        horarioCompleto.addAll(horariosDelCurso);
                    }
                }
            }
        }

        // Ordenar el horario para una mejor visualización
        // (Requiere que HorarioCurso tenga getters para dia y horaInicio)
        if (!horarioCompleto.isEmpty()) {
            horarioCompleto.sort(Comparator.comparing((HorarioCurso hc) -> hc.getDia().ordinal()) // Asumiendo que DiaSemana es un Enum
                    .thenComparing(HorarioCurso::getHoraInicio));
        }
        return horarioCompleto;
    }

}