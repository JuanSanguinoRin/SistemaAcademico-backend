package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Rol;
import co.edu.ufps.backend.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolService {
    @Autowired
    private final RolRepository rolRepository;

    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }

    public Optional<Rol> getRolById(Long id) {
        return rolRepository.findById(id);
    }

    public Rol createRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public Rol updateRol(Long id, Rol rolDetails) {
        return rolRepository.findById(id).map(rol -> {
            return rolRepository.save(rol);
        }).orElseThrow(() -> new RuntimeException("Rol not found"));
    }

    public void deleteRol(Long id) {
        rolRepository.deleteById(id);
    }
}