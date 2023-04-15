package webling.coffee.backend.global.enums;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import webling.coffee.backend.global.errors.codes.EnumValueErrorCode;
import webling.coffee.backend.global.errors.exceptions.RestBusinessException;

import java.util.Locale;

@Getter
public enum Team {
    P3,
    FRONTEND,
    MOBILE,
    OROUND_DEV,

    ;

    public static Team of(@NotBlank final String name) {
        try {
            return Team.valueOf(name.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new RestBusinessException(EnumValueErrorCode.TEAM_VALUE_INVALID);
        }
    }

}
