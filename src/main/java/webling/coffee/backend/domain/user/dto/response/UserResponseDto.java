package webling.coffee.backend.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.domain.user.entity.User;
import webling.coffee.backend.global.enums.Team;
import webling.coffee.backend.global.enums.UserRole;

import java.time.LocalDate;

import static webling.coffee.backend.global.constant.ValidationFormats.LOCAL_DATE_PATTERN;

public class UserResponseDto {

    @Getter
    @Setter
    @Builder
    @Schema(name = "UserResponseRegister")
    public static class Register {
        private String email;
        private String username;
        private String nickname;
        private String phoneNumber;
        @JsonFormat(pattern = LOCAL_DATE_PATTERN)
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
    @Schema(name = "UserResponseLogin")
    public static class Login {
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
        @JsonIgnore
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
    @Schema(name = "UserResponseUpdate")
    public static class Update {
        private Long userId;
        private String email;
        private String username;
        private String nickname;
        private String userRole;
        private String phoneNumber;

        public static Update toDto(User user) {
            return Update.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .nickname(user.getNickname())
                    .userRole(user.getUserRole().name())
                    .phoneNumber(user.getPhoneNumber())
                    .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    @Schema(name = "UserResponseFind")
    public static class Find {
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

        @QueryProjection
        public Find(final Long userId, final String email, final String username, final String nickname, final String phoneNumber, final LocalDate birthDate, final UserRole userRole, final Integer stamps, final Team teamName) {
            this.userId = userId;
            this.email = email;
            this.username = username;
            this.nickname = nickname;
            this.phoneNumber = phoneNumber;
            this.birthDate = birthDate;
            this.userRole = userRole.name();
            this.stamps = stamps;
            this.teamName = teamName.name();
        }

        public static Find toDto(final User user) {
            return Find.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .nickname(user.getNickname())
                    .phoneNumber(user.getPhoneNumber())
                    .birthDate(user.getBirthDate())
                    .userRole(user.getUserRole().name())
                    .stamps(user.getStamps())
                    .teamName(user.getTeamName().name())
                    .build();
        }
    }
}
