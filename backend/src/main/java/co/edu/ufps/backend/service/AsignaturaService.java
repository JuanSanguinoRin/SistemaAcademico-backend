package co.edu.ufps.backend.service;
import co.edu.ufps.backend.repository.AsignaturaPrerrequisitoRepository;
import co.edu.ufps.backend.model.Asignatura;
import co.edu.ufps.backend.model.AsignaturaPrerrequisito;
import co.edu.ufps.backend.repository.AsignaturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsignaturaService {

    @Autowired
    private final AsignaturaRepository asignaturaRepository;

    @Autowired
    private final AsignaturaPrerrequisitoService asignaturaPrerrequisitoService;

    private final AsignaturaPrerrequisitoRepository asignaturaPrerrequisitoRepository;

    // Obtener todas las asignaturas
    public List<Asignatura> getAllAsignaturas() {
        return asignaturaRepository.findAll();
    }

    // Obtener una asignatura por su código
    public Optional<Asignatura> getAsignaturaByCodigo(Long codigo) {
        return asignaturaRepository.findById(codigo);
    }

    // Crear una nueva asignatura
    public Asignatura createAsignatura(Asignatura asignatura) {
        return asignaturaRepository.save(asignatura);
    }

    // Actualizar una asignatura existente
    public Asignatura updateAsignatura(Long codigo, Asignatura asignaturaDetails) {
        return asignaturaRepository.findById(codigo).map(asignatura -> {
            asignatura.setNombre(asignaturaDetails.getNombre());
            asignatura.setSemestre(asignaturaDetails.getSemestre());
            asignatura.setHoras(asignaturaDetails.getHoras());
            asignatura.setTipoAsignatura(asignaturaDetails.getTipoAsignatura());
            asignatura.setAsignatura(asignaturaDetails.getAsignatura());
            return asignaturaRepository.save(asignatura);
        }).orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));
    }

    // Eliminar una asignatura por su código
    public void deleteAsignatura(Long codigo) {
        asignaturaRepository.deleteById(codigo);
    }

    // Métodos de la clase
    public AsignaturaPrerrequisito agregarPrerrequisito(Long codigoAsignatura, Long codigoPrerrequisito) {
        Asignatura asignatura = asignaturaRepository.findById(codigoAsignatura)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));

        Asignatura prerrequisito = asignaturaRepository.findById(codigoPrerrequisito)
                .orElseThrow(() -> new RuntimeException("Prerrequisito no encontrado"));

        AsignaturaPrerrequisito nuevaRelacion = new AsignaturaPrerrequisito();
        nuevaRelacion.setCodigo(System.currentTimeMillis()); // O usa un ID autogenerado
        nuevaRelacion.setAsignatura(asignatura);
        nuevaRelacion.setPrerrequisito(prerrequisito);

        return asignaturaPrerrequisitoService.createAsignaturaPrerrequisito(nuevaRelacion);

    }

    public void eliminarPrerrequisito(Long codigoAsignatura, Long codigoPrerrequisito) {
        AsignaturaPrerrequisito relacion = asignaturaPrerrequisitoRepository
                .findByAsignatura_CodigoAndPrerrequisito_Codigo(codigoAsignatura, codigoPrerrequisito)
                .orElseThrow(() -> new RuntimeException("Relación de prerrequisito no encontrada"));


        asignaturaPrerrequisitoRepository.delete(relacion);
    }
}