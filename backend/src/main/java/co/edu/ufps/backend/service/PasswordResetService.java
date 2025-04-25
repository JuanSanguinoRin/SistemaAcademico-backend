package co.edu.ufps.backend.service;

import co.edu.ufps.backend.dto.ForgotPasswordRequestDTO;
import co.edu.ufps.backend.dto.ResetPasswordRequestDTO;
import co.edu.ufps.backend.model.PasswordResetToken;
import co.edu.ufps.backend.model.Usuario;
import co.edu.ufps.backend.repository.PasswordResetTokenRepository;
import co.edu.ufps.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    // Tiempo de expiración del token: 1 hora
    private static final int EXPIRATION_HOURS = 1;

    public boolean processForgotPassword(ForgotPasswordRequestDTO request) {
        Optional<Usuario> usuarioOpt = findUserByEmail(request.getEmail());

        if (usuarioOpt.isEmpty()) {
            // No enviamos error para evitar divulgación de información
            return false;
        }

        Usuario usuario = usuarioOpt.get();

        // Eliminar token anterior si existe
        tokenRepository.findByUsuario(usuario).ifPresent(tokenRepository::delete);

        // Generar un nuevo token
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUsuario(usuario);
        resetToken.setFechaExpiracion(LocalDateTime.now().plusHours(EXPIRATION_HOURS));
        tokenRepository.save(resetToken);

        // Enviar correo con enlace para restablecer contraseña
        String resetUrl = "http://tuaplicacion.com/reset-password?token=" + token;
        String emailText = "Hola,\n\n" +
                "Has solicitado restablecer tu contraseña. Haz clic en el siguiente enlace para cambiarla:\n\n" +
                resetUrl + "\n\n" +
                "Si no solicitaste cambiar tu contraseña, ignora este correo.\n\n" +
                "El enlace será válido durante " + EXPIRATION_HOURS + " hora(s).";

        emailService.sendEmail(
                request.getEmail(),
                "Restablecimiento de contraseña",
                emailText
        );

        return true;
    }

    public boolean validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(token);

        if (tokenOpt.isEmpty() || tokenOpt.get().isExpired()) {
            return false;
        }

        return true;
    }

    public boolean resetPassword(ResetPasswordRequestDTO request) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(request.getToken());

        if (tokenOpt.isEmpty() || tokenOpt.get().isExpired()) {
            return false;
        }

        PasswordResetToken resetToken = tokenOpt.get();
        Usuario usuario = resetToken.getUsuario();

        // Actualizar contraseña
        usuario.setPassword(passwordEncoder.encode(request.getNewPassword()));
        usuarioRepository.save(usuario);

        // Eliminar token usado
        tokenRepository.delete(resetToken);

        return true;
    }

    // Método auxiliar para encontrar un usuario por email
    private Optional<Usuario> findUserByEmail(String email) {
        // Aquí buscamos usuarios por email
        // Suponiendo que tienes un método para buscar por correo electrónico
        // Si no lo tienes, necesitarás implementarlo en tu UsuarioRepository

        // Para este ejemplo, asumimos que el email puede estar en cualquiera de los campos de correo:
        return usuarioRepository.findAll().stream()
                .filter(u -> (u.getPersona() != null &&
                        (email.equals(u.getPersona().getCorreoElectronico()) ||
                                email.equals(u.getPersona().getCorreoInstitucional()))))
                .findFirst();
    }
}