package webling.coffee.backend.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webling.coffee.backend.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository <Order, Long>, QueryOrderRepository {
}
