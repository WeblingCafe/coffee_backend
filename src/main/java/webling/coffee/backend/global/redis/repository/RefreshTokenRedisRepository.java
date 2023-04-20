package webling.coffee.backend.global.redis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webling.coffee.backend.global.redis.entity.RefreshToken;

public interface RefreshTokenRedisRepository extends JpaRepository<RefreshToken, String> {
}
