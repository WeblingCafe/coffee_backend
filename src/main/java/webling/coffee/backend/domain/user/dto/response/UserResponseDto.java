package webling.coffee.backend.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.global.enums.UserRole;

import java.time.LocalDate;

public class UserResponseDto {

    @Getter
    @Setter
    @Builder
    public static class Register {
        private String email;
        private String username;
        private String nickname;
        private String phoneNumber;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;
        private String userRole;
        private String teamName;

        public static Register toDto(final @NotNull User entity) {
            return Register.builder()
                    .email(entity.getEmail())
                    .username(entity.getUsername())
                    .nickname(entity.getNickname())
                    .phoneNumber(entity.getPhoneNumber())
                    .birthDate(entity.getBirthDate())
                    .userRole(entity.getUserRole().name())
                    .teamName(entity.getTeamName().name())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Login {
        private Long userId;
        private String email;
        private String username;
        private String nickname;
        private String phoneNumber;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;
        private String userRole;
        private Integer stamps;
        private String teamName;
        private String refreshToken;

        public static Login toDto(final @NotNull User user, final @NotBlank String refreshToken) {
            return Login.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .nickname(user.getNickname())
                    .phoneNumber(user.getPhoneNumber())
                    .birthDate(user.getBirthDate())
                    .userRole(user.getUserRole().name())
                    .stamps(user.getStamps())
                    .teamName(user.getTeamName().name())
                    .refreshToken(refreshToken)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Update {
        private Long userId;
        private String email;
        private String username;
        private String nickname;
        private String phoneNumber;

    }
}
