package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Semestre;
import co.edu.ufps.backend.service.SemestreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/semestres")
@RequiredArgsConstructor
public class SemestreController {

    private final SemestreService semestreService;

    /**
     * Obtener todos los semestres
     * @return Lista de semestres
     */
    @GetMapping
    public List<Semestre> getAllSemestres() {
        return semestreService.getAllSemestres();
    }

    /**
     * Obtener un semestre por su ID
     * @param id ID del semestre
     * @return El semestre encontrado
     */
    @GetMapping("/{id}")
    public Semestre getSemestreById(@PathVariable Long id) {
        return semestreService.getSemestreById(id)
                .orElseThrow(() -> new RuntimeException("Semestre no encontrado"));
    }

    /**
     * Crear un nuevo semestre
     * @param semestre Datos del semestre a crear
     * @return El semestre creado
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Semestre createSemestre(@RequestBody Semestre semestre) {
        return semestreService.createSemestre(semestre);
    }

    /**
     * Actualizar un semestre existente
     * @param id ID del semestre a actualizar
     * @param semestreDetails Nuevos detalles del semestre
     * @return El semestre actualizado
     */
    @PutMapping("/{id}")
    public Semestre updateSemestre(@PathVariable Long id, @RequestBody Semestre semestreDetails) {
        return semestreService.updateSemestre(id, semestreDetails);
    }

    /**
     * Eliminar un semestre por su ID
     * @param id ID del semestre a eliminar
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSemestre(@PathVariable Long id) {
        semestreService.deleteSemestre(id);
    }
}
