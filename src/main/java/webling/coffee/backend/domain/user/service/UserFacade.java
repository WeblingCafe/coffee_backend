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
import webling.coffee.backend.global.responses.errors.codes.UserErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;

import java.util.List;

import static webling.coffee.backend.domain.user.dto.response.UserResponseDto.Register;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserFacade {

    private final UserService userService;

    public Register register(final @NotNull UserRequestDto.Register request) {

        if (userService.checkDuplicationUser(request)) {
            throw new RestBusinessException(UserErrorCode.DUPLICATION);
        }

        return UserResponseDto.Register.toDto(userService.register(request));
    }

    public UserResponseDto.Update update(final @NotNull Long userId,
                                         final @NotNull UserRequestDto.UpdateInfo request) {

        User user = userService.findById(userId);

        checkUserIsAvailable(user);

        return UserResponseDto.Update.toDto(userService.update(user, request));
    }

    public UserResponseDto.Update updateRole(final @NotNull Long userId,
                                             final @NotNull UserRequestDto.UpdateRole request) {

        User user = userService.findById(userId);

        checkUserIsAvailable(user);

        return UserResponseDto.Update.toDto(userService.updateRole(user, request));
    }

    @Transactional (readOnly = true)
    public List<UserResponseDto.Find> findAllByIsAvailableTrue() {
        return userService.findAllByIsAvailableTrue();
    }

    @Transactional (readOnly = true)
    public UserResponseDto.Find findById(final @NotNull Long id) {
        User user = userService.findById(id);

        checkUserIsAvailable(user);

        return UserResponseDto.Find.toDto(user);
    }

    private void checkUserIsAvailable (final @NotNull User user) {
        if (!user.getIsAvailable()) {
            throw new RestBusinessException.Failure(UserErrorCode.NOT_AVAILABLE);
        }
    }

    public void delete(final @NotNull Long userId) {
        User user = userService.findByIdAndIsAvailableTrue(userId);
        userService.delete(user);
    }

}
