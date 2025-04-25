package co.edu.ufps.backend.dto;

import co.edu.ufps.backend.model.RolEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String username;
    private Long personaId;
    private String nombre;
    private Set<RolEnum> roles;
}