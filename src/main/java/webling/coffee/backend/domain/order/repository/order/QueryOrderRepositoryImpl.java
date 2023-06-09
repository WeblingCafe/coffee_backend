package webling.coffee.backend.domain.order.repository.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import webling.coffee.backend.domain.order.entity.Order;
import webling.coffee.backend.global.enums.OrderStatus;

import java.util.List;

import static webling.coffee.backend.domain.order.entity.QOrder.order;
import static webling.coffee.backend.domain.order.entity.QOrderCart.orderCart;
import static webling.coffee.backend.domain.user.entity.QUser.user;

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

    @Override
    public List<Order> findMeOrderedAll(final @NotNull Long userId) {
        return jpaQueryFactory.selectFrom(order)
                .where(
                        order.user.userId.eq(userId),
                        order.orderStatus.eq(OrderStatus.ORDERED)
                )
                .fetch()
                ;
    }

    @Override
    public Order findByOrderIdAndOrderedFetchUserAndOrderCart(final Long orderId) {
        return jpaQueryFactory.selectFrom(order)
                .join(order.user, user)
                .fetchJoin()
                .join(order.orderCart, orderCart)
                .fetchJoin()
                .where(
                        order.orderId.eq(orderId),
                        order.orderStatus.eq(OrderStatus.ORDERED)
                )
                .fetchFirst()
                ;
    }

    @Override
    public Order findByUserIdAndOrderIdAndOrderedFetchUserAndOrderCart(Long userId, Long orderId) {
        return jpaQueryFactory.selectFrom(order)
                .join(order.user, user)
                .fetchJoin()
                .join(order.orderCart, orderCart)
                .fetchJoin()
                .where(
                        user.userId.eq(userId),
                        order.orderId.eq(orderId),
                        order.orderStatus.eq(OrderStatus.ORDERED)
                )
                .fetchFirst()
                ;
    }
}
