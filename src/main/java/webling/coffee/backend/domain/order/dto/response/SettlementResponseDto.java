package webling.coffee.backend.domain.order.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.domain.order.entity.OrderCart;
import webling.coffee.backend.global.responses.errors.codes.OrderErrorCode;
import webling.coffee.backend.global.responses.errors.codes.UserErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static webling.coffee.backend.global.constant.ValidationFormats.LOCAL_DATE_TIME_PATTERN;

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

        private Long settlementPrice;

        private Long settlementCouponAmount;

        public static User from (final @NotNull webling.coffee.backend.domain.user.entity.User user) {

            if (user == null) {
                return User.builder().build();
            }

            List<Cart> orderCartList = new ArrayList<>(user.getOrderCart())
                    .stream()
                    .map(Cart::from)
                    .sorted(Comparator.comparing(Cart::getCartId))
                    .collect(Collectors.toList());

            return User.builder()
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .nickName(user.getNickname())
                    .orderCart(orderCartList)
                    .settlementPrice(orderCartList
                                    .stream().mapToLong(Cart::getTotalPrice).sum())
                    .settlementCouponAmount(orderCartList
                                    .stream().mapToLong(Cart::getUsedCouponAmount).sum())
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
        @JsonFormat(pattern = LOCAL_DATE_TIME_PATTERN)
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
                                    .sorted(Comparator.comparing(Order::getOrderId))
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
