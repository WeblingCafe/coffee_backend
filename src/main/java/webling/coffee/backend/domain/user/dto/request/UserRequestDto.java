package webling.coffee.backend.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.global.annotation.VerifyEnum;
import webling.coffee.backend.global.enums.Team;

import java.time.LocalDate;

@Getter
@Setter
@Valid
public class UserRequestDto {

    @Getter
    @Setter
    @Valid
    public static class Register {

        @Email
        private String email;
        private String username;
        private String nickname;
        private String password;
        private String phoneNumber;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;
        @VerifyEnum(enumClass = Team.class, ignoreCase = true)
        private String team;
    }

    @Getter
    @Setter
    public static class Login {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class Update {

        private String username;
        private String nickname;
        private String password;
        private String phoneNumber;
    }

    @Getter
    @Setter
    public static class UpdateRole {

        private String userRole;
    }
}
