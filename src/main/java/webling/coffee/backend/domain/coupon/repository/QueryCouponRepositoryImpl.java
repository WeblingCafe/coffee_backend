package webling.coffee.backend.domain.coupon.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import webling.coffee.backend.domain.coupon.dto.response.CouponResponseDto;
import webling.coffee.backend.domain.coupon.dto.response.QCouponResponseDto_Find;
import webling.coffee.backend.domain.coupon.entity.Coupon;
import webling.coffee.backend.domain.user.entity.User;

import java.util.List;

import static webling.coffee.backend.domain.coupon.entity.QCoupon.coupon;

@RequiredArgsConstructor
public class QueryCouponRepositoryImpl implements QueryCouponRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Coupon> findAllByUserAndIsAvailable(User user) {
        return jpaQueryFactory.selectFrom(coupon)
                .where(
                        coupon.user.eq(user),
                        coupon.isAvailable.isTrue()
                )
                .fetch();
    }

    @Override
    public List<CouponResponseDto.Find> findAllByMeOnStatus(final @NotNull User user, final @NotBlank String status) {
        return jpaQueryFactory.select(new QCouponResponseDto_Find(
                coupon.couponId,
                coupon.couponType,
                coupon.regDate,
                coupon.user.email
        ))
                .from(coupon)
                .where(
                        coupon.user.eq(user),
                        checkStatus(status)
                )
                .fetch();
    }

    private BooleanExpression checkStatus (final @NotBlank String status) {

        if (!StringUtils.hasText(status)) {
            return null;
        }

        if (status.equals("available")) {
            return coupon.isAvailable.isTrue();
        }

        if (status.equals("expired")) {
            return coupon.isAvailable.isFalse();
        }

        return null;
    }
}
