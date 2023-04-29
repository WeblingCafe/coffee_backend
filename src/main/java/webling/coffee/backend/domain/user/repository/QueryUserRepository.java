package webling.coffee.backend.domain.user.repository;

import jakarta.validation.constraints.NotBlank;
import webling.coffee.backend.domain.user.dto.response.UserResponseDto;

import java.util.List;

public interface QueryUserRepository {

    boolean checkUserByEmail(final @NotBlank String email);

    List<UserResponseDto.Find> findAllByIsAvailableTrue();
}
