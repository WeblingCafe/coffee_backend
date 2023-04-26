package webling.coffee.backend.domain.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.domain.order.entity.Order;

public class OrderResponseDto {

    @Getter
    @Setter
    @Builder
    public static class Create {

        private Long orderId;
        private String user;
        private String recipient;
        private Long totalPrice;
        private boolean isPersonalCup;
        private String request;

        public static Create toDto(Order order) {
            return Create.builder()
                    .orderId(order.getOrderId())
                    .user(order.getUser().getEmail())
                    .recipient(order.getRecipientEmail())
                    .totalPrice(order.getTotalPrice())
                    .isPersonalCup(order.isPersonalCup())
                    .request(order.getRequest())
                    .build();
        }
    }

}
