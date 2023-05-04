package webling.coffee.backend.domain.order.repository.order;

import webling.coffee.backend.domain.order.dto.response.OrderResponseDto;
import webling.coffee.backend.domain.order.entity.Order;

import java.util.List;

public interface QueryOrderRepository {

    List<Order> findOrderedAll();

}
