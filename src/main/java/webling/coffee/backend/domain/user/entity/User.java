package webling.coffee.backend.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import webling.coffee.backend.domain.coupon.entity.Coupon;
import webling.coffee.backend.domain.order.entity.Order;
import webling.coffee.backend.domain.user.dto.request.UserRequestDto;
import webling.coffee.backend.global.base.BaseTime;
import webling.coffee.backend.global.enums.Team;
import webling.coffee.backend.global.utils.EncodingUtils;

import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@Table(name = "user_mst")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseTime {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    private String username;

    @NotNull
    @JsonIgnore
    private String password;

    @NotNull
    private Boolean isManager;

    @NotNull
    private Boolean isAvailable;

    @Enumerated(EnumType.STRING)
    private Team teamName;
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
    @OneToMany(mappedBy = "user")
    private List<Coupon> coupons;

    public static User register(UserRequestDto.Register request) {
        return User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(EncodingUtils.encode(request.getPassword()))
                .isManager(request.getIsManager())
                .isAvailable(true)
                .teamName(request.getTeam())
                .build();
    }
}
