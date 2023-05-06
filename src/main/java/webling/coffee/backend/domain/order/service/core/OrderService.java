package webling.coffee.backend.domain.order.service.core;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.menu.entity.Menu;
import webling.coffee.backend.domain.order.dto.request.OrderRequestDto;
import webling.coffee.backend.domain.order.entity.Order;
import webling.coffee.backend.domain.order.entity.OrderCart;
import webling.coffee.backend.domain.order.repository.order.OrderRepository;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.global.responses.errors.codes.MenuErrorCode;
import webling.coffee.backend.global.responses.errors.codes.OrderErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;

import java.util.List;

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

        checkColdAndHotAvailable(menu, request);
        return orderRepository.save(Order.create(user, recipient, menu, getTotalPrice(menu.getPrice(), request.getAmount()), request));
    }

    public void addCart (OrderCart cart, List<Order> orderList) {
        orderList.forEach(order -> {
            Order.addCart(order, cart);
        });
    }

    private Long getTotalPrice (Long menuPrice, Long amount) {
        return menuPrice * amount;
    }

    private void checkColdAndHotAvailable (final @NotNull Menu menu,
                                           final @NotNull OrderRequestDto.Create request) {

        if (request.isCold() && !menu.isColdAvailable()) {
            throw new RestBusinessException.Failure(MenuErrorCode.COLD_NOT_AVAILABLE);
        }

        if (!request.isCold() && !menu.isHotAvailable()) {
            throw new RestBusinessException.Failure(MenuErrorCode.HOT_NOT_AVAILABLE);
        }
    }

    @Transactional (readOnly = true)
    public List<Order> findOrderedAll() {
        return orderRepository.findOrderedAll();
    }

    @Transactional (readOnly = true)
    public List<Order> findMeOrderedAll(final @NotNull Long userId) {
        return orderRepository.findMeOrderedAll(userId);
    }

    public Order findByOrderIdAndOrderedFetchUserAndOrderCart(final @NotNull Long orderId) {
        Order order = orderRepository.findByOrderIdAndOrderedFetchUserAndOrderCart(orderId);

        if (order == null) {
            throw new RestBusinessException.NotFound(OrderErrorCode.NOT_FOUNT);
        }

        return order;
    }

    public void cancelOrder(final @NotNull Order order) {
        orderRepository.save(Order.cancel(order));
    }

    public Order completeOrder(final @NotNull Order order) {
        return orderRepository.save(Order.complete(order));
    }

}
