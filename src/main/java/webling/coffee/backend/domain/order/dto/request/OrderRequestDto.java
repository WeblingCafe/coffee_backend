package webling.coffee.backend.domain.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webling.coffee.backend.domain.order.dto.response.OrderResponseDto;
import webling.coffee.backend.domain.order.entity.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Create {

        private Long menuId;
        private Long userId;
        private Long recipientId;
        private Long amount;
        private Long couponAmount;
        private boolean isCold;
        private boolean isPersonalCup;
        private String request;

        public static OrderResponseDto.Create toDto(Order order) {
            return null;
        }
    }
}
