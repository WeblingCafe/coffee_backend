package webling.coffee.backend.domain.user.service.core;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.user.dto.request.UserRequestDto;
import webling.coffee.backend.domain.user.dto.response.UserResponseDto;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.domain.user.repository.UserRepository;
import webling.coffee.backend.global.errors.codes.UserErrorCode;
import webling.coffee.backend.global.errors.exceptions.RestBusinessException;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean checkDuplicationUser (UserRequestDto.Register request) {
        return userRepository.checkUserByEmail(request.getEmail());
    }

    public UserResponseDto.Register register(UserRequestDto.Register request) {
        return UserResponseDto.Register.toDto(userRepository.save(User.register(request)));
    }

    @Transactional(readOnly = true)
    public User findByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RestBusinessException(UserErrorCode.USER_NOT_FOUND));
    }

    public User findById(final @NotNull Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RestBusinessException(UserErrorCode.USER_NOT_FOUND));
    }
}
