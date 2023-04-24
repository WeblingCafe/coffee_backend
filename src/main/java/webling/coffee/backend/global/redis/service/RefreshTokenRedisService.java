package webling.coffee.backend.global.redis.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.global.redis.entity.RefreshToken;
import webling.coffee.backend.global.redis.repository.RefreshTokenRedisRepository;
import webling.coffee.backend.global.responses.errors.codes.AuthenticationErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;
import webling.coffee.backend.global.utils.JwtUtils;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenRedisService {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    private final JwtUtils jwtUtils;

    public RefreshToken save(final @NotBlank Long id,
                             final @NotBlank String email,
                             final @NotNull Integer expirationTime) {
        return refreshTokenRedisRepository.save(
                RefreshToken.builder()
                        .key(email)
                        .value(jwtUtils.generateRefreshToken(id, email))
                        .expiration(expirationTime)
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public RefreshToken findByUsername (String username) {
        return refreshTokenRedisRepository.findById(username)
                .orElseThrow(() -> new RestBusinessException.NotFound(AuthenticationErrorCode.REFRESH_TOKEN_NOT_FOUND));
    }

    @Transactional
    public void deleteById (final @NotBlank String username) {
        refreshTokenRedisRepository.deleteById(username);
    }
}
