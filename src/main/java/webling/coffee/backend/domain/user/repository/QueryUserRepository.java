package webling.coffee.backend.domain.user.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import webling.coffee.backend.domain.order.dto.request.SettlementRequestDto;
import webling.coffee.backend.domain.user.dto.response.UserResponseDto;
import webling.coffee.backend.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface QueryUserRepository {

    boolean checkUserByEmail(final @NotBlank String email);

    List<UserResponseDto.Find> findAllByIsAvailableTrue();

    Optional<User> findByIdAndIsAvailableTrue(Long id);

    List<User> settlementAllBySearchOptions(final @NotNull SettlementRequestDto.Search request);

    User settlementMeBySearchOptions(final @NotNull User user, final @NotNull SettlementRequestDto.RegDate request);

}
