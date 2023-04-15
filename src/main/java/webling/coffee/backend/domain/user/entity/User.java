package webling.coffee.backend.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import webling.coffee.backend.domain.coupon.entity.Coupon;
import webling.coffee.backend.domain.order.entity.Order;
import webling.coffee.backend.domain.user.dto.request.UserRequestDto;
import webling.coffee.backend.global.base.BaseTime;
import webling.coffee.backend.global.enums.Team;
import webling.coffee.backend.global.enums.UserRole;
import webling.coffee.backend.global.utils.EncodingUtils;

import java.time.LocalDate;
import java.util.List;

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
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

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
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
    @OneToMany(mappedBy = "user")
    private List<Coupon> coupons;

    public static User register(UserRequestDto.Register request) {
        return User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(EncodingUtils.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .birthDate(request.getBirthDate())
                .userRole(UserRole.of(request.getUserRole()))
                .isAvailable(true)
                .stamps(0)
                .teamName(Team.of(request.getTeam()))
                .build();
    }
}
