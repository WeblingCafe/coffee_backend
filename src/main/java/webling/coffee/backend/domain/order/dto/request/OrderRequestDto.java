package webling.coffee.backend.domain.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class OrderRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Cart {

        private Long couponAmount;
        private List<Create> orderList;

    }

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

        public Long getStamps (Long amount, Long couponAmount) {
            return amount - couponAmount;
        }
    }
}
