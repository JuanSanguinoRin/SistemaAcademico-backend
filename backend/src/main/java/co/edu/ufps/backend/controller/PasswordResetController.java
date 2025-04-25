package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.dto.ForgotPasswordRequestDTO;
import co.edu.ufps.backend.dto.ResetPasswordRequestDTO;
import co.edu.ufps.backend.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/password")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/forgot")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequestDTO request) {
        passwordResetService.processForgotPassword(request);
        // Siempre devolvemos éxito para evitar enumeración de usuarios
        return ResponseEntity.ok().body(
                Map.of("message", "Si el correo está registrado, recibirás instrucciones para restablecer tu contraseña")
        );
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        boolean isValid = passwordResetService.validatePasswordResetToken(token);

        if (isValid) {
            return ResponseEntity.ok().body(Map.of("valid", true));
        } else {
            return ResponseEntity.badRequest().body(
                    Map.of("valid", false, "message", "Token inválido o expirado")
            );
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequestDTO request) {
        boolean success = passwordResetService.resetPassword(request);

        if (success) {
            return ResponseEntity.ok().body(
                    Map.of("message", "Contraseña actualizada correctamente")
            );
        } else {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "Token inválido o expirado")
            );
        }
    }
}