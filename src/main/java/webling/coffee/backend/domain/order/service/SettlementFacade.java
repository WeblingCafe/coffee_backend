package webling.coffee.backend.domain.order.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.order.dto.request.SettlementRequestDto;
import webling.coffee.backend.domain.order.dto.response.SettlementResponseDto;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.domain.user.service.core.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class SettlementFacade {

    private final UserService userService;

    public List<SettlementResponseDto.User> settlementAllBySearchOptions(final @NotNull SettlementRequestDto.Search request) {
        return userService.settlementAllBySearchOptions(request)
                .stream()
                .map(SettlementResponseDto.User::from)
                .sorted(Comparator.comparing(SettlementResponseDto.User::getUserId))
                .collect(Collectors.toList());
    }

    public SettlementResponseDto.User settlementMeBySearchOptions(final @NotNull Long userId,
                                                                  final @NotNull SettlementRequestDto.BaseField request) {

        User user = userService.findByIdAndIsAvailableTrue(userId);

        return SettlementResponseDto.User.from(userService.settlementMeBySearchOptions(user, request));
    }

}
