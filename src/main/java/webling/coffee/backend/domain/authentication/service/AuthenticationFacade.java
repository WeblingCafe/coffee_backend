package webling.coffee.backend.domain.authentication.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.user.dto.request.UserRequestDto;
import webling.coffee.backend.domain.user.dto.response.UserResponseDto;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.domain.user.service.core.UserService;
import webling.coffee.backend.global.redis.service.RefreshTokenRedisService;
import webling.coffee.backend.global.responses.errors.codes.UserErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;
import webling.coffee.backend.global.utils.EncodingUtils;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class AuthenticationFacade {

    private final UserService userService;

    private final RefreshTokenRedisService refreshTokenRedisService;

    @Value("${jwt.refresh.timeout-of-minutes}")
    private int refreshTokenExpirationTime;


    public UserResponseDto.Login login(final @NotNull UserRequestDto.Login request) {

        User user = userService.findByEmail(request.getEmail());

        if(EncodingUtils.isNotMatches(request.getPassword(), user.getPassword())) {
            throw new RestBusinessException(UserErrorCode.PASSWORD_MISMATCH);
        }

        refreshTokenRedisService.save(user.getUserId(), user.getEmail(), refreshTokenExpirationTime);

        return UserResponseDto.Login.toDto (user);
    }

    public void logout(final Long userId) {
        User user = userService.findById(userId);
    }

}
