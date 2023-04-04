package webling.coffee.backend.domain.user.entity;

import jakarta.persistence.*;
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
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String username;
    private String password;
    private Boolean isManager;

    @OneToMany
    private List<Order> orders;

    @OneToMany
    private List<Coupon> coupons;

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }
}
