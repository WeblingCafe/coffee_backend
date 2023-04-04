package webling.coffee.backend.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import webling.coffee.backend.domain.coupon.entity.Coupon;
import webling.coffee.backend.domain.order.entity.Order;
import webling.coffee.backend.global.base.BaseTime;
import webling.coffee.backend.global.enums.Team;

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

    @Column(unique = true)
    private String email;
    private String username;
    @JsonIgnore
    private String password;
    private Boolean isManager;

    @Enumerated(EnumType.STRING)
    private Team teamName;
    @OneToMany
    private List<Order> orders;

    @OneToMany
    private List<Coupon> coupons;

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }
}
