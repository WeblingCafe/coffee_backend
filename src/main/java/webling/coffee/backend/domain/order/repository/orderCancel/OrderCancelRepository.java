package webling.coffee.backend.domain.order.repository.orderCancel;

import org.springframework.data.jpa.repository.JpaRepository;
import webling.coffee.backend.domain.order.entity.OrderCancel;

public interface OrderCancelRepository extends JpaRepository<OrderCancel, Long> {

}
