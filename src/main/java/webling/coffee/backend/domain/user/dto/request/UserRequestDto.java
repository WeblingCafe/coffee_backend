package webling.coffee.backend.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.global.annotation.VerifyEnum;
import webling.coffee.backend.global.enums.Team;
import webling.coffee.backend.global.enums.UserRole;

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
        private String password;
        private String phoneNumber;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;

        @VerifyEnum(enumClass = UserRole.class, ignoreCase = true)
        private UserRole userRole;

        @VerifyEnum(enumClass = Team.class, ignoreCase = true)
        private Team team;
    }

    @Getter
    @Setter
    public static class Login {
        private String email;
        private String password;
    }

}
