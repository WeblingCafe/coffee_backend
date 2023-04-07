package webling.coffee.backend.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.global.enums.Team;
import webling.coffee.backend.global.enums.UserRole;

import java.time.LocalDate;

public class UserRequestDto {

    @Getter
    @Setter
    public static class Register {

        @Email
        private String email;
        private String username;
        private String password;
        private String phoneNumber;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;
        private UserRole userRole;
        private Team team;
    }
}
