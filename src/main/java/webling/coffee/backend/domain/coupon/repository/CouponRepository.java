package webling.coffee.backend.domain.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webling.coffee.backend.domain.coupon.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long>, QueryCouponRepository {
}
