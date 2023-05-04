package webling.coffee.backend.domain.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.domain.order.entity.Order;

public class OrderResponseDto {

    @Getter
    @Setter
    @Builder
    @Schema(name = "OrderResponseCreate")
    public static class Create {

        private Long orderId;
        private String user;
        private String recipient;
        private String menuName;
        private Long totalPrice;
        private boolean isPersonalCup;
        private String request;

        public static Create toDto(Order order) {
            return Create.builder()
                    .orderId(order.getOrderId())
                    .user(order.getUser().getEmail())
                    .recipient(order.getRecipientEmail())
                    .menuName(order.getMenu().getMenuName())
                    .totalPrice(order.getTotalPrice())
                    .isPersonalCup(order.isPersonalCup())
                    .request(order.getRequest())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @Schema (name = "OrderResponseFind")
    public static class Find {
        private Long orderId;
        private String user;
        private String recipient;
        private String menuName;
        private Long totalPrice;
        private boolean isPersonalCup;
        private String request;

        public static Find toDto (Order order) {
            return Find.builder()
                    .orderId(order.getOrderId())
                    .user(order.getUser().getEmail())
                    .recipient(order.getRecipientEmail())
                    .menuName(order.getMenu().getMenuName())
                    .totalPrice(order.getTotalPrice())
                    .isPersonalCup(order.isPersonalCup())
                    .request(order.getRequest())
                    .build();
        }
    }

}
