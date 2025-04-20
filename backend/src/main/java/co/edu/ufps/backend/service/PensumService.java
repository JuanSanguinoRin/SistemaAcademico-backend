package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Asignatura;
import co.edu.ufps.backend.model.Pensum;
import co.edu.ufps.backend.repository.AsignaturaRepository;
import co.edu.ufps.backend.repository.PensumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PensumService {

    @Autowired
    private final PensumRepository pensumRepository;

    @Autowired
    private final AsignaturaRepository asignaturaRepository;

    // Obtener todos los pensums
    public List<Pensum> getAllPensums() {
        return pensumRepository.findAll();
    }

    // Obtener un pensum por su ID
    public Optional<Pensum> getPensumById(Long id) {
        return pensumRepository.findById(id);
    }

    // Crear un nuevo pensum
    public Pensum createPensum(Pensum pensum) {
        return pensumRepository.save(pensum);
    }

    // Actualizar un pensum existente
    public Pensum updatePensum(Long id, Pensum pensumDetails) {
        return pensumRepository.findById(id).map(pensum -> {
            pensum.setNombre(pensumDetails.getNombre());
            pensum.setCreditosTotales(pensumDetails.getCreditosTotales());
            pensum.setPrograma(pensumDetails.getPrograma());
            pensum.setDuracion(pensumDetails.getDuracion());
            pensum.setAnio(pensumDetails.getAnio());
            return pensumRepository.save(pensum);
        }).orElseThrow(() -> new RuntimeException("Pensum no encontrado"));
    }

    // Eliminar un pensum por su ID
    public void deletePensum(Long id) {
        pensumRepository.deleteById(id);
    }

    // Agregar asignatura a un pensum
    public Asignatura agregarAsignatura(Long idPensum, Asignatura asignatura) {
        Pensum pensum = pensumRepository.findById(idPensum)
                .orElseThrow(() -> new RuntimeException("Pensum no encontrado"));

        asignatura.setPensum(pensum);
        return asignaturaRepository.save(asignatura);
    }

    // Eliminar una asignatura de un pensum
    public void eliminarAsignatura(Long codigoAsignatura) {
        Asignatura asignatura = asignaturaRepository.findById(codigoAsignatura)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));

        asignaturaRepository.delete(asignatura);
    }

    // Modificar información del pensum (puede servir como helper adicional)
    public Pensum modificarPensum(Long id, String nuevoNombre, Integer nuevaDuracion) {
        return pensumRepository.findById(id).map(pensum -> {
            pensum.setNombre(nuevoNombre);
            pensum.setDuracion(nuevaDuracion);
            return pensumRepository.save(pensum);
        }).orElseThrow(() -> new RuntimeException("Pensum no encontrado"));
    }
}
