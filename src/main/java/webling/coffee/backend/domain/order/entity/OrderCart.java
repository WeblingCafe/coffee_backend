package webling.coffee.backend.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import webling.coffee.backend.domain.order.dto.request.OrderRequestDto;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.global.base.BaseTime;

import java.util.List;

import static webling.coffee.backend.global.constant.CalculationOperators.COUPON_VALUE;

@Getter
@Setter(AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@DynamicUpdate
@Table(name = "cart_mst")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCart extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_CART_ID")
    private Long orderCartId;

    @NotNull
    private Long originalPrice;

    private Long usedCouponAmount;

    @NotNull
    private Long totalPrice;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "USER_ID")
    private User user;

    @OneToMany (mappedBy = "orderCart")
    private List<Order> orderList;

    public static OrderCart toEntity(final @NotNull Long originalPrice,
                                     final @NotNull Long usedCouponAmount,
                                     final @NotNull List<Order> orderEntityList,
                                     final @NotNull User user) {
        return OrderCart.builder()
                .originalPrice(originalPrice)
                .usedCouponAmount(usedCouponAmount)
                .totalPrice(originalPrice - (usedCouponAmount * COUPON_VALUE))
                .orderList(orderEntityList)
                .user(user)
                .build();
    }

    public static OrderCart refundOrder(final @NotNull OrderCart orderCart) {

        if (orderCart.getUsedCouponAmount() != null) {
            orderCart.setTotalPrice(orderCart.getTotalPrice() + (orderCart.getUsedCouponAmount() * COUPON_VALUE));
            orderCart.setUsedCouponAmount(0L);
        }

        return orderCart;
    }

}
