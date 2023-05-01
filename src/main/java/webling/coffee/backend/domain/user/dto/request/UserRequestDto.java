package webling.coffee.backend.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Valid
public class UserRequestDto {

    @Getter
    @Setter
    @Valid
    @Schema(name = "UserRequestRegister")
    public static class Register {

        @Email
        private String email;
        private String username;
        private String nickname;
        private String password;
        private String phoneNumber;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;
        private String team;
    }

    @Getter
    @Setter
    @Schema(name = "UserRequestLogin")
    public static class Login {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    @Schema(name = "UserRequestUpdateInfo")
    public static class UpdateInfo {

        private String username;
        private String nickname;
        private String password;
        private String phoneNumber;
    }

    @Getter
    @Setter
    @Schema(name = "UserRequestUpdateRole")
    public static class UpdateRole {

        private String userRole;
    }
}
