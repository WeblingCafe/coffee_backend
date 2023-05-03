package webling.coffee.backend.domain.order.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.domain.order.entity.OrderCart;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SettlementResponseDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    @Schema(name = "SettlementResponseUser")
    public static class User {
        private Long userId;

        private String username;

        private String nickName;

        private List<Cart> orderCart;

        public static User from (final @NotNull webling.coffee.backend.domain.user.entity.User user) {
            return User.builder()
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .nickName(user.getNickname())
                    .orderCart(user.getOrderCart() != null ?
                                    new ArrayList<>(user.getOrderCart())
                                            .stream()
                                    .map(Cart::from)
                                    .sorted(Comparator.comparing(Cart::getCartId))
                                    .collect(Collectors.toList()) : null
                            )
                    .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    @Schema(name = "SettlementResponseCart")
    public static class Cart {
        private Long cartId;
        private Long originalPrice;
        private Long usedCouponAmount;
        private Long totalPrice;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime regDate;
        private List<Order> orderList;

        public static Cart from (final @NotNull OrderCart orderCart) {
            return Cart.builder()
                    .cartId(orderCart.getOrderCartId())
                    .originalPrice(orderCart.getOriginalPrice())
                    .usedCouponAmount(orderCart.getUsedCouponAmount())
                    .totalPrice(orderCart.getTotalPrice())
                    .regDate(orderCart.getRegDate())
                    .orderList(orderCart.getOrderList() != null ?
                                    new ArrayList<>(orderCart.getOrderList())
                                            .stream()
                                    .map(Order::from)
                                    .collect(Collectors.toList()) : null
                            )
                    .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    @Schema(name = "SettlementResponseOrder")
    public static class Order {
        private Long orderId;
        private String menuName;
        private String orderRequest;

        public static Order from (final @NotNull webling.coffee.backend.domain.order.entity.Order order) {
            return Order.builder()
                    .orderId(order.getOrderId())
                    .menuName(order.getMenu().getMenuName())
                    .orderRequest(order.getRequest())
                    .build();
        }
    }

}
