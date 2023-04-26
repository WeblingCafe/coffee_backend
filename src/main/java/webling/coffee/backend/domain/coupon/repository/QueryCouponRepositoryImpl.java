package webling.coffee.backend.domain.coupon.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
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
}
