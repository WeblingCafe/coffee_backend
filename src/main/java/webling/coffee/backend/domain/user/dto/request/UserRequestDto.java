package webling.coffee.backend.domain.user.dto.request;

import lombok.Getter;
import lombok.Setter;

public class UserRequestDto {

    @Getter
    @Setter
    public static class Register {
        private String email;
        private String username;
        private String password;
        private Boolean isManager;
    }
}
