package webling.coffee.backend.domain.coupon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import webling.coffee.backend.domain.coupon.dto.request.CouponRequestDto;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.global.base.BaseTime;
import webling.coffee.backend.global.enums.CouponType;


@Getter
@Setter(AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@Table(name = "coupon_mst")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Coupon extends BaseTime {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long couponId;

    @Enumerated(EnumType.STRING)
    private CouponType couponType;

    private boolean isAvailable;

    private String regEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "USER_ID")
    private User user;

    public static Coupon toEntity(final String adminEmail,
                                  final User user,
                                  final CouponRequestDto.Create request) {
        return Coupon.builder()
                .couponType(CouponType.of(request.getCouponType()))
                .isAvailable(true)
                .user(user)
                .regEmail(adminEmail)
                .build();
    }

    public static Coupon issueCoupons(final @NotNull User user, final @NotNull CouponType couponType) {
        return Coupon.builder()
                .couponType(couponType)
                .isAvailable(true)
                .user(user)
                .regEmail("SYSTEM")
                .build();
    }

    public static Coupon disable(Coupon coupon) {

        coupon.setAvailable(false);
        return coupon;
    }
}
