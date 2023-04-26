package webling.coffee.backend.global.enums;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import webling.coffee.backend.global.responses.errors.codes.EnumValueErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;

import java.util.Arrays;
import java.util.Locale;

public enum UserRole {

    MANAGER,
    EMPLOYEE,
    GUEST,
    DEVELOPER,

    ;

    public static boolean contains(@NotBlank final String name) {
        return Arrays.stream(UserRole.values())
                .map(UserRole::name)
                .toList()
                .contains(name.toUpperCase());
    }

    public static UserRole of(@NotBlank final String name) {
        try {
            return UserRole.valueOf(name.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new RestBusinessException.NotFound(EnumValueErrorCode.USER_ROLE_VALUE_INVALID);
        }
    }

    public static boolean isAllMatch(@NotBlank final String roles) {
        return Arrays.stream(roles.toUpperCase(Locale.ROOT).split(","))
                .allMatch(UserRole::contains);
    }

    public boolean contains(@NotEmpty final UserRole[] roles) {
        return Arrays.stream(roles)
                .toList()
                .contains(this);
    }
}
