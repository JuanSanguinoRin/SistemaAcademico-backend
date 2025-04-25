package co.edu.ufps.backend.service;

import co.edu.ufps.backend.dto.LoginRequestDTO;
import co.edu.ufps.backend.dto.LoginResponseDTO;
import co.edu.ufps.backend.dto.RegistroUsuarioDTO;
import co.edu.ufps.backend.model.Persona;
import co.edu.ufps.backend.model.Rol;
import co.edu.ufps.backend.model.RolEnum;
import co.edu.ufps.backend.model.Usuario;
import co.edu.ufps.backend.repository.PersonaRepository;
import co.edu.ufps.backend.repository.RolRepository;
import co.edu.ufps.backend.repository.UsuarioRepository;
import co.edu.ufps.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PersonaRepository personaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public Usuario registrarUsuario(RegistroUsuarioDTO registroDTO) {
        if (usuarioRepository.existsByUsername(registroDTO.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        Persona persona = personaRepository.findById(registroDTO.getPersonaId())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        Set<Rol> roles = new HashSet<>();
        for (RolEnum rolNombre : registroDTO.getRoles()) {
            Rol rol = rolRepository.findByNombre(rolNombre)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolNombre));
            roles.add(rol);
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(registroDTO.getUsername());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
        usuario.setRoles(roles);
        usuario.setPersona(persona);

        return usuarioRepository.save(usuario);
    }

    public LoginResponseDTO autenticar(LoginRequestDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Usuario usuario = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.generarToken(userDetails);

        return LoginResponseDTO.builder()
                .token(token)
                .username(usuario.getUsername())
                .personaId(usuario.getPersona().getId())
                .nombre(usuario.getPersona().getNombre())
                .roles(usuario.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet()))
                .build();
    }
}
