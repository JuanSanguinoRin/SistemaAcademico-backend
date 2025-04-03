package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.AsignaturaPrerrequisito;
import co.edu.ufps.backend.repository.AsignaturaPrerrequisitoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsignaturaPrerrequisitoService {

    @Autowired
    private final AsignaturaPrerrequisitoRepository asignaturaPrerrequisitoRepository;

    // Obtener todos los prerrequisitos de asignaturas
    public List<AsignaturaPrerrequisito> getAllAsignaturasPrerrequisito() {
        return asignaturaPrerrequisitoRepository.findAll();
    }

    // Obtener un prerrequisito por su código
    public Optional<AsignaturaPrerrequisito> getAsignaturaPrerrequisitoByCodigo(Long codigo) {
        return asignaturaPrerrequisitoRepository.findById(codigo);
    }

    // Crear un nuevo prerrequisito
    public AsignaturaPrerrequisito createAsignaturaPrerrequisito(AsignaturaPrerrequisito asignaturaPrerrequisito) {
        return asignaturaPrerrequisitoRepository.save(asignaturaPrerrequisito);
    }

    // Actualizar un prerrequisito existente
    public AsignaturaPrerrequisito updateAsignaturaPrerrequisito(Long codigo, AsignaturaPrerrequisito asignaturaPrerrequisitoDetails) {
        return asignaturaPrerrequisitoRepository.findById(codigo).map(asignaturaPrerrequisito -> {
            asignaturaPrerrequisito.setAsignatura(asignaturaPrerrequisitoDetails.getAsignatura());
            asignaturaPrerrequisito.setPrerrequisito(asignaturaPrerrequisitoDetails.getPrerrequisito());
            return asignaturaPrerrequisitoRepository.save(asignaturaPrerrequisito);
        }).orElseThrow(() -> new RuntimeException("Asignatura Prerrequisito no encontrada"));
    }

    // Eliminar un prerrequisito por su código
    public void deleteAsignaturaPrerrequisito(Long codigo) {
        asignaturaPrerrequisitoRepository.deleteById(codigo);
    }
}