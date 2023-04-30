package webling.coffee.backend.domain.coupon.dto.request;

import lombok.Getter;
import lombok.Setter;

public class CouponRequestDto {

    @Getter
    @Setter
    public static class Create {
        private String couponType;
    }
}
