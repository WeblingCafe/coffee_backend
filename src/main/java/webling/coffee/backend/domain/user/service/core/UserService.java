package webling.coffee.backend.domain.user.service.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.user.dto.request.UserRequestDto;
import webling.coffee.backend.domain.user.dto.response.UserResponseDto;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.domain.user.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public boolean checkDuplicationUser (UserRequestDto.Register request) {
        return userRepository.checkDuplicationUser(request.getEmail());
    }

    public UserResponseDto.Register register(UserRequestDto.Register request) {
        return UserResponseDto.Register.toDto(userRepository.save(User.register(request)));
    }

}
