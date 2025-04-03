package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Material;
import co.edu.ufps.backend.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialService {
    @Autowired
    private final MaterialRepository materialRepository;

    public List<Material> getAllMateriales() {
        return materialRepository.findAll();
    }

    public Optional<Material> getMaterialById(Long id) {
        return materialRepository.findById(id);
    }

    public Material createMaterial(Material material) {
        return materialRepository.save(material);
    }

    public Material updateMaterial(Long id, Material materialDetails) {
        return materialRepository.findById(id).map(material -> {
            material.setNombre(materialDetails.getNombre());
            material.setEstado(materialDetails.getEstado());
            material.setTipo(materialDetails.getTipo());
            return materialRepository.save(material);
        }).orElseThrow(() -> new RuntimeException("Material not found"));
    }

    public void deleteMaterial(Long id) {
        materialRepository.deleteById(id);
    }
}

