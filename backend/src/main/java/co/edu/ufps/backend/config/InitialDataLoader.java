package co.edu.ufps.backend.config;

import co.edu.ufps.backend.model.Rol;
import co.edu.ufps.backend.model.RolEnum;
import co.edu.ufps.backend.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {

    private final RolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {
        // Crear roles si no existen
        crearRolSiNoExiste(RolEnum.Admin);
        crearRolSiNoExiste(RolEnum.Docente);
        crearRolSiNoExiste(RolEnum.Estudiante);
        crearRolSiNoExiste(RolEnum.PersonalAdministrativo);
    }

    private void crearRolSiNoExiste(RolEnum rolEnum) {
        try {
            // Usa exactamente el mismo nombre, sin transformaciones
            Optional<Rol> rolExistente = rolRepository.findByNombre(rolEnum);

            if (rolExistente.isEmpty()) {
                Rol rol = new Rol();
                rol.setNombre(rolEnum);
                rol.setDescripcion("Rol de " + rolEnum.toString());
                rolRepository.save(rol);
                System.out.println("Rol creado: " + rolEnum.toString());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Nombre de rol inválido: " + rolEnum.toString());
        }
    }
}