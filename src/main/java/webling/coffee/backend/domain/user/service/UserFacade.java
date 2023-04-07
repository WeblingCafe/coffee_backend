package webling.coffee.backend.domain.user.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.user.dto.request.UserRequestDto;
import webling.coffee.backend.domain.user.dto.response.UserResponseDto;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.domain.user.service.core.UserService;
import webling.coffee.backend.global.errors.codes.UserErrorCode;
import webling.coffee.backend.global.errors.exceptions.RestBusinessException;
import webling.coffee.backend.global.utils.EncodingUtils;

import static webling.coffee.backend.domain.user.dto.response.UserResponseDto.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserFacade {

    private final UserService userService;

    public Register register(final @NotNull UserRequestDto.Register request) {

        if (userService.checkDuplicationUser(request)) {
            throw new RestBusinessException(UserErrorCode.USER_DUPLICATION);
        }

        return userService.register(request);
    }

    public Login login(final @NotNull UserRequestDto.Login request) {

        User user = userService.findByEmail(request.getEmail());

        if(EncodingUtils.isNotMatches(request.getPassword(), user.getPassword())) {
            throw new RestBusinessException(UserErrorCode.PASSWORD_MISMATCH);
        }

        return Login.toDto (user);
    }

}
