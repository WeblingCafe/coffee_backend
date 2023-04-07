package webling.coffee.backend.domain.user.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import webling.coffee.backend.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, QueryUserRepository {

    Optional<User> findByEmail(final @NotBlank String email);

}
