package webling.coffee.backend.domain.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class OrderRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(name = "orderRequestCart")
    public static class Cart {

        private Long couponAmount;
        private List<Create> orderList;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(name = "orderRequestCreate")
    public static class Create {

        private Long menuId;
        private Long userId;
        private Long recipientId;
        private Long amount;
        private boolean isCold;
        private boolean isPersonalCup;
        private String request;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(name = "orderRequestCancel")
    public static class Cancel {
        private String cancelMessage;
    }
}
