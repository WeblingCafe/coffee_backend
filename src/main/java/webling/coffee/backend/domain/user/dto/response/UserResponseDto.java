package webling.coffee.backend.domain.user.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webling.coffee.backend.domain.user.entity.User;

public class UserResponseDto {

    @Getter
    @Setter
    @Builder
    public static class Register {
        private String email;
        private String username;
        private String teamName;
        private boolean isManager;

        public static Register toDto(final @NotNull User entity) {
            return Register.builder()
                    .email(entity.getEmail())
                    .username(entity.getUsername())
                    .teamName(entity.getTeamName().name())
                    .isManager(entity.getIsManager())
                    .build();
        }
    }
}
