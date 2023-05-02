package webling.coffee.backend.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.order.dto.response.SettlementResponseDto;
import webling.coffee.backend.domain.user.service.core.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class SettlementFacade {

    private final UserService userService;

    public List<SettlementResponseDto.User> settlementAll() {
        return userService.settlementAll()
                .stream()
                .map(SettlementResponseDto.User::from)
                .collect(Collectors.toList());
    }
}
