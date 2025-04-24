package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.PersonalAdministrativo;
import co.edu.ufps.backend.service.PersonalAdministrativoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personal-administrativo")
@RequiredArgsConstructor
public class PersonalAdministrativoController {

    private final PersonalAdministrativoService personalAdministrativoService;

    // Obtener todo el personal administrativo
    @GetMapping
    public ResponseEntity<List<PersonalAdministrativo>> getAllPersonalAdministrativo() {
        return ResponseEntity.ok(personalAdministrativoService.getAllPersonalAdministrativo());
    }

    // Obtener personal administrativo por ID
    @GetMapping("/{id}")
    public ResponseEntity<PersonalAdministrativo> getPersonalAdministrativoById(@PathVariable Long id) {
        Optional<PersonalAdministrativo> personal = personalAdministrativoService.getPersonalAdministrativoById(id);
        return personal.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear nuevo personal administrativo
    @PostMapping
    public ResponseEntity<PersonalAdministrativo> createPersonalAdministrativo(@RequestBody PersonalAdministrativo personal) {
        try {
            PersonalAdministrativo nuevoPersonal = personalAdministrativoService.createPersonalAdministrativo(personal);
            return ResponseEntity.ok(nuevoPersonal);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Actualizar datos del personal administrativo
    @PutMapping("/{id}")
    public ResponseEntity<PersonalAdministrativo> updatePersonalAdministrativo(
            @PathVariable Long id,
            @RequestBody PersonalAdministrativo personalDetalles) {
        try {
            PersonalAdministrativo personalActualizado = personalAdministrativoService.updatePersonalAdministrativo(id, personalDetalles);
            return ResponseEntity.ok(personalActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar personal administrativo (lógica o física)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonalAdministrativo(@PathVariable Long id) {
        try {
            personalAdministrativoService.deletePersonalAdministrativo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}