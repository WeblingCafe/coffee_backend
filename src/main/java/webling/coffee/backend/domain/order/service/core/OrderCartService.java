package webling.coffee.backend.domain.order.service.core;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.order.entity.Order;
import webling.coffee.backend.domain.order.entity.OrderCart;
import webling.coffee.backend.domain.order.repository.orderCart.OrderCartRepository;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.global.responses.errors.codes.OrderErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;

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
        Long originalPrice = orderEntityList.stream().mapToLong(Order::getTotalPrice).sum();
        return orderCartRepository.save(OrderCart.toEntity(originalPrice, couponAmount, orderEntityList, user));
    }

    public void refundOrder(final OrderCart orderCart, final Long cancelOrderTotalPrice) {
        orderCartRepository.save(OrderCart.refundOrder(orderCart, cancelOrderTotalPrice));
    }

    @Transactional (readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public OrderCart findByOrderCartId(final Long orderCartId) {
        return orderCartRepository.findById(orderCartId)
                .orElseThrow(() -> new RestBusinessException.NotFound(OrderErrorCode.CART_NOT_FOUNT));
    }

}
