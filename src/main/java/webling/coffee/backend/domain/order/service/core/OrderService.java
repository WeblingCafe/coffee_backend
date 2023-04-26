package webling.coffee.backend.domain.order.service.core;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.menu.entity.Menu;
import webling.coffee.backend.domain.order.dto.request.OrderRequestDto;
import webling.coffee.backend.domain.order.entity.Order;
import webling.coffee.backend.domain.order.repository.OrderRepository;
import webling.coffee.backend.domain.user.entity.User;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    public Order create(final @NotNull User user,
                        final @NotNull User recipient,
                        final @NotNull Menu menu,
                        final @NotNull OrderRequestDto.Create request) {

        return orderRepository.save(Order.create(user, recipient, menu, getTotalPrice(menu.getPrice(), request.getAmount()), request));
    }

    private Long getTotalPrice (Long menuPrice, Long amount) {
        return menuPrice * amount;
    }
}
