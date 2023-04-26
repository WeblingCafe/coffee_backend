package webling.coffee.backend.domain.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    }
}
