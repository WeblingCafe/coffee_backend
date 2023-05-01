package webling.coffee.backend.global.enums;

import lombok.Getter;
import webling.coffee.backend.global.responses.errors.codes.EnumValueErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;

import java.util.Locale;

@Getter
public enum BoardCategory {

    EVENT,

    ANNOUNCEMENT,

    ;

    public static BoardCategory of (String name) {
        try {
            return BoardCategory.valueOf(name.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new RestBusinessException(EnumValueErrorCode.BOARD_CATEGORY_VALUE_INVALID);
        }
    }
}
