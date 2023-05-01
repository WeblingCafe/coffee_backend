package webling.coffee.backend.domain.coupon.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class CouponRequestDto {

    @Getter
    @Setter
    @Schema (name = "couponRequestCreate")
    public static class Create {
        private String couponType;
    }
}
