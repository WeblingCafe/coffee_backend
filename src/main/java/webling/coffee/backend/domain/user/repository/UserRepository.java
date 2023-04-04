package webling.coffee.backend.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webling.coffee.backend.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, QueryUserRepository {
}
