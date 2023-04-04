package webling.coffee.backend.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;
import webling.coffee.backend.domain.coupon.entity.Coupon;
import webling.coffee.backend.domain.order.entity.Order;
import webling.coffee.backend.global.base.BaseTime;

import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseTime {

    @Id
    @GeneratedValue
    private Long userId;
    private String email;
    private String username;
    private String password;
    private Boolean isManager;

    @OneToMany
    private List<Order> orders;

    @OneToMany
    private List<Coupon> coupons;
}
