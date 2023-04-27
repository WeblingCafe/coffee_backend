package webling.coffee.backend.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.global.base.BaseTime;

import java.util.List;

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

    private Long totalPrice;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "USER_ID")
    private User user;

    @OneToMany (mappedBy = "orderCart")
    private List<Order> orderList;

    public static OrderCart toEntity(final @NotNull Long totalCount,
                                     final @NotNull List<Order> orderEntityList,
                                     final @NotNull User user) {
        return OrderCart.builder()
                .totalPrice(totalCount)
                .orderList(orderEntityList)
                .user(user)
                .build();
    }
}
