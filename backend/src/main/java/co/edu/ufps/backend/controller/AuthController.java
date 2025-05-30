package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.dto.LoginRequestDTO;
import co.edu.ufps.backend.dto.LoginResponseDTO;
import co.edu.ufps.backend.dto.RegistroUsuarioDTO;
import co.edu.ufps.backend.model.Usuario;
import co.edu.ufps.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody RegistroUsuarioDTO registroDTO) {
        return ResponseEntity.ok(authService.registrarUsuario(registroDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginDTO) {
        return ResponseEntity.ok(authService.autenticar(loginDTO));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API funcionando correctamente");
    }

}