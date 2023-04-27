package webling.coffee.backend.domain.coupon.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webling.coffee.backend.domain.coupon.entity.Coupon;
import webling.coffee.backend.domain.coupon.repository.CouponRepository;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.global.responses.errors.codes.CouponErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;

import java.util.List;

import static webling.coffee.backend.global.constant.CalculationOperators.STAMP_MAX_NUM;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional(readOnly = true)
    public List<Coupon> findAllByUserAndIsAvailable (final @NotNull User user) {
        return couponRepository.findAllByUserAndIsAvailable(user);
    }

    public void useCoupons(final @NotNull List<Coupon> coupons,
                           final @NotNull Long couponAmount) {

        if (coupons.size() < couponAmount) {
            throw new RestBusinessException.Failure(CouponErrorCode.EXCEEDED_AMOUNT);
        }

        disableCoupons (coupons, couponAmount);
    }

    private void disableCoupons (List<Coupon> coupons, Long couponAmount) {
        for (int i = 0; i < couponAmount; i++) {
            coupons.set(i, Coupon.disable(coupons.get(i)));
        }
        couponRepository.saveAll(coupons);
    }

    public void issueCouponByStamp(User updatedUser) {
        int issueCouponNumber = updatedUser.getStamps() / STAMP_MAX_NUM;

        if (issueCouponNumber > 0) {
            for (int i = 0; i < issueCouponNumber; i++) {
                couponRepository.save(Coupon.toEntity(updatedUser));
            }
        }

        User.useStamps(updatedUser, issueCouponNumber * STAMP_MAX_NUM);
    }
}
