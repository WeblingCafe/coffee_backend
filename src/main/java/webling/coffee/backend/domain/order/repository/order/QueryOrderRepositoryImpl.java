package webling.coffee.backend.domain.order.repository.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import webling.coffee.backend.domain.order.entity.Order;
import webling.coffee.backend.global.enums.OrderStatus;

import java.util.List;

import static webling.coffee.backend.domain.order.entity.QOrder.order;

@RequiredArgsConstructor
public class QueryOrderRepositoryImpl implements QueryOrderRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Order> findOrderedAll() {
        return jpaQueryFactory.selectFrom(order)
                .where(
                        order.orderStatus.eq(OrderStatus.ORDERED)
                )
                .fetch()
                ;
    }

}
