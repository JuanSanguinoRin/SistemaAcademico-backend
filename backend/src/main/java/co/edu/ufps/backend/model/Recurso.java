package co.edu.ufps.backend.model;
import java.util.Date;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // o JOINED o TABLE_PER_CLASS
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String ubicacion;
    private String estadoMateria;

}
