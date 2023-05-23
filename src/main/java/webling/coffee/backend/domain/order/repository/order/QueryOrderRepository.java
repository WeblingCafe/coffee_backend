package webling.coffee.backend.domain.order.repository.order;

import jakarta.validation.constraints.NotNull;
import webling.coffee.backend.domain.order.entity.Order;

import java.util.List;

public interface QueryOrderRepository {

    List<Order> findOrderedAll();

    List<Order> findMeOrderedAll(final @NotNull Long userId);

    Order findByOrderIdAndOrderedFetchUserAndOrderCart(final @NotNull Long orderId);

    Order findByUserIdAndOrderIdAndOrderedFetchUserAndOrderCart(final @NotNull Long userId, final @NotNull Long orderId);
}
