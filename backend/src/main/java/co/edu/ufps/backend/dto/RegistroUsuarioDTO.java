package co.edu.ufps.backend.dto;

import co.edu.ufps.backend.model.RolEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsuarioDTO {
    private String username;
    private String password;
    private Set<RolEnum> roles;
    private Long personaId;
}
