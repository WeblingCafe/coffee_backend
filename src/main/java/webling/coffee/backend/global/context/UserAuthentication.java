package webling.coffee.backend.global.context;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import webling.coffee.backend.domain.coupon.entity.Coupon;
import webling.coffee.backend.domain.user.entity.User;

import java.time.LocalDate;
import java.util.List;

import static webling.coffee.backend.global.constant.ValidationFormats.LOCAL_DATE_PATTERN;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequiredArgsConstructor
public class UserAuthentication {

    private Long userId;
    private String email;
    private String username;
    private String nickname;
    private String phoneNumber;
    @JsonFormat(pattern = LOCAL_DATE_PATTERN)
    private LocalDate birthDate;
    private String userRole;
    private Integer stamps;
    private String teamName;
    private Long couponAmount;

    public static UserAuthentication from (User user) {
        return UserAuthentication.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .birthDate(user.getBirthDate())
                .userRole(user.getUserRole().name())
                .stamps(user.getStamps())
                .teamName(user.getTeamName().name())
                .couponAmount(user.getCoupons().stream().filter(Coupon::isAvailable).count())
                .build();
    }
}
