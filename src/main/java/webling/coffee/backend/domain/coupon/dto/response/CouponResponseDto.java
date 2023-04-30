package webling.coffee.backend.domain.coupon.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.domain.coupon.entity.Coupon;
import webling.coffee.backend.global.enums.CouponType;

import java.time.LocalDateTime;

public class CouponResponseDto {

    @Getter
    @Setter
    @Builder
    public static class Create {

        private Long couponId;

        private String couponType;

        private String userEmail;

        public static Create toDto (final @NotNull Coupon coupon) {
            return Create.builder()
                    .couponId(coupon.getCouponId())
                    .couponType(coupon.getCouponType().name())
                    .userEmail(coupon.getUser().getEmail())
                    .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Find {

        private Long couponId;

        private String couponType;

        private LocalDateTime regDate;

        private String userEmail;

        @QueryProjection
        public Find(final Long couponId, final CouponType couponType, final LocalDateTime regDate, final String userEmail) {
            this.couponId = couponId;
            this.couponType = couponType.name();
            this.regDate = regDate;
            this.userEmail = userEmail;
        }
    }
}
