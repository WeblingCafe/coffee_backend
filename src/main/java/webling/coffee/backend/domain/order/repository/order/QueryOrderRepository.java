package webling.coffee.backend.domain.order.repository.order;

import jakarta.validation.constraints.NotNull;
import webling.coffee.backend.domain.order.entity.Order;

import java.util.List;

public interface QueryOrderRepository {

    List<Order> findOrderedAll();

    List<Order> findMeOrderedAll(final @NotNull Long userId);

}
