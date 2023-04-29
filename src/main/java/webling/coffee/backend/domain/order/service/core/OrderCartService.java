package webling.coffee.backend.domain.order.service.core;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.order.entity.Order;
import webling.coffee.backend.domain.order.entity.OrderCart;
import webling.coffee.backend.domain.order.repository.orderCart.OrderCartRepository;
import webling.coffee.backend.domain.user.entity.User;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderCartService {

    private final OrderCartRepository orderCartRepository;

    public OrderCart save(final @NotNull List<Order> orderEntityList,
                          final @NotNull User user,
                          final @NotNull Long couponAmount) {
        Long originalPrice = orderEntityList.stream().map(Order::getTotalPrice).reduce(Long::sum).orElseGet(() -> 0L);
        return orderCartRepository.save(OrderCart.toEntity(originalPrice, couponAmount, orderEntityList, user));
    }
}
