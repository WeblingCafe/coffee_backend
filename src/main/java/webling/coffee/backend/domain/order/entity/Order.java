package webling.coffee.backend.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import webling.coffee.backend.domain.cart.entity.Cart;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.global.base.BaseTime;
import webling.coffee.backend.global.enums.OrderStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter (AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@Table(name = "order_mst")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order extends BaseTime {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long orderId;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "USER_ID")
    private User user;

    @NotNull
    private Long totalPrice;
    private boolean isPersonalCup;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Builder.Default
    @OneToMany(mappedBy = "order")
    private List<Cart> orderMenus = new ArrayList<>();
}
