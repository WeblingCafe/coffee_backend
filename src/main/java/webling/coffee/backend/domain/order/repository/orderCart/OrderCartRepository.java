package webling.coffee.backend.domain.order.repository.orderCart;

import org.springframework.data.jpa.repository.JpaRepository;
import webling.coffee.backend.domain.order.entity.OrderCart;

public interface OrderCartRepository extends JpaRepository<OrderCart, Long> {
}
