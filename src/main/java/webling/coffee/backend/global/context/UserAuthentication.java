package webling.coffee.backend.global.context;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import webling.coffee.backend.domain.user.entity.User;

import java.time.LocalDate;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserAuthentication {

    private Long userId;
    private String email;
    private String username;
    private String phoneNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String userRole;
    private String teamName;

    public static UserAuthentication from (User user) {
        return UserAuthentication.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .birthDate(user.getBirthDate())
                .userRole(user.getUserRole().name())
                .teamName(user.getTeamName().name())
                .build();
    }
}
