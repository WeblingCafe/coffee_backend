package webling.coffee.backend.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import webling.coffee.backend.domain.menu.entity.Menu;
import webling.coffee.backend.domain.order.dto.request.OrderRequestDto;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.global.base.BaseTime;
import webling.coffee.backend.global.enums.OrderStatus;

@Getter
@Setter (AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@DynamicUpdate
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

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    private String recipientEmail;
    @NotNull
    private Long totalPrice;
    private boolean isPersonalCup;
    private String request;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public static Order create(User user,
                               User recipient,
                               Menu menu,
                               Long totalPrice,
                               OrderRequestDto.Create request) {
        return Order.builder()
                .user(user)
                .menu(menu)
                .recipientEmail(recipient.getEmail())
                .totalPrice(totalPrice)
                .isPersonalCup(request.isPersonalCup())
                .request(request.getRequest())
                .orderStatus(OrderStatus.ORDERED)
                .build();
    }
}
