package webling.coffee.backend.domain.coupon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "USER_ID")
    private User user;

    public static Coupon toEntity(final @NotNull User user) {
        return Coupon.builder()
                .couponType(CouponType.COMMON)
                .isAvailable(true)
                .user(user)
                .build();
    }

    public static Coupon disable(Coupon coupon) {

        coupon.setAvailable(false);
        return coupon;
    }
}
