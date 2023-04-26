package webling.coffee.backend.domain.coupon.repository;

import jakarta.validation.constraints.NotNull;
import webling.coffee.backend.domain.coupon.entity.Coupon;
import webling.coffee.backend.domain.user.entity.User;

import java.util.List;

public interface QueryCouponRepository {

    List<Coupon> findAllByUserAndIsAvailable (final @NotNull User user);
}
