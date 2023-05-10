package webling.coffee.backend.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;
import webling.coffee.backend.domain.coupon.entity.Coupon;
import webling.coffee.backend.domain.menu.entity.FavoriteMenu;
import webling.coffee.backend.domain.order.entity.Order;
import webling.coffee.backend.domain.order.entity.OrderCart;
import webling.coffee.backend.domain.user.dto.request.UserRequestDto;
import webling.coffee.backend.global.base.BaseTime;
import webling.coffee.backend.global.enums.Team;
import webling.coffee.backend.global.enums.UserRole;
import webling.coffee.backend.global.utils.EncodingUtils;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter(AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@DynamicUpdate
@Table(name = "user_mst")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseTime {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Column(nullable = false)
    private Boolean isAvailable;

    private Integer stamps;

    @Enumerated(EnumType.STRING)
    private Team teamName;

    @OrderBy("orderId asc")
    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    @OrderBy("couponId asc")
    @OneToMany(mappedBy = "user")
    private Set<Coupon> coupons;

    @OrderBy("orderCartId asc")
    @OneToMany(mappedBy = "user")
    private Set<OrderCart> orderCart;

    @OrderBy("favoriteMenuId asc")
    @OneToMany(mappedBy = "user")
    private Set<FavoriteMenu> favoriteMenuList;

    public static User register(UserRequestDto.Register request) {
        return User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .nickname(request.getNickname())
                .password(EncodingUtils.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .birthDate(request.getBirthDate())
                .userRole(UserRole.EMPLOYEE)
                .isAvailable(true)
                .stamps(0)
                .teamName(Team.of(request.getTeam()))
                .build();
    }

    public static User update(final @NotNull User user,
                              final @NotNull UserRequestDto.UpdateInfo request) {

        if (StringUtils.hasText(request.getUsername()))
            user.setUsername(request.getUsername());

        if (StringUtils.hasText(request.getNickname()))
            user.setNickname(request.getNickname());

        if (StringUtils.hasText(request.getPhoneNumber()))
            user.setPhoneNumber(request.getPhoneNumber());

        if (StringUtils.hasText(request.getPassword()))
            user.setPassword(EncodingUtils.encode(request.getPassword()));

        return user;
    }

    public static User updateRole(final @NotNull User user,
                                  final @NotNull UserRequestDto.UpdateRole request) {

        if (StringUtils.hasText(request.getUserRole()))
            user.setUserRole(UserRole.of(request.getUserRole()));

        return user;
    }

    public static User addStamps(final @NotNull User user, final @NotNull Integer totalAmount) {

        user.setStamps(user.getStamps() + totalAmount);
        return user;
    }

    public static void useStamps(final @NotNull User user, final @NotNull int usedStamps) {
        user.setStamps(user.getStamps() - usedStamps);
    }

    public static User refundStamp(final User user, final Integer totalPrice) {
        user.setStamps(user.getStamps() - totalPrice);
        return user;
    }

    public static void delete(final User user) {
        user.setIsAvailable(false);
    }

}
