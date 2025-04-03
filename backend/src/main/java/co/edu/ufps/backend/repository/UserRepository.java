package co.edu.ufps.backend.repository;
import co.edu.ufps.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}

