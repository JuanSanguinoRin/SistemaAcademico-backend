package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Aula;
import co.edu.ufps.backend.service.AulaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aulas")
@RequiredArgsConstructor
public class AulaController {

    @Autowired
    private final AulaService aulaService;

    @GetMapping
    public ResponseEntity<List<Aula>> getAllAulas() {
        List<Aula> aulas = aulaService.getAllAulas();
        return new ResponseEntity<>(aulas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aula> getAulaById(@PathVariable Long id) {
        return aulaService.getAulaById(id)
                .map(aula -> new ResponseEntity<>(aula, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Aula> createAula(@RequestBody Aula aula) {
        Aula newAula = aulaService.createAula(aula);
        return new ResponseEntity<>(newAula, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aula> updateAula(@PathVariable Long id, @RequestBody Aula aula) {
        try {
            Aula updatedAula = aulaService.updateAula(id, aula);
            return new ResponseEntity<>(updatedAula, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAula(@PathVariable Long id) {
        aulaService.deleteAula(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}