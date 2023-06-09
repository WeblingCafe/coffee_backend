package webling.coffee.backend.global.enums;

import webling.coffee.backend.global.responses.errors.codes.EnumValueErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;

import java.util.Locale;

public enum CouponType {

    JOIN,
    BIRTHDAY,
    COMMON,
    REFUND,


    ;

    public static CouponType of (String name) {
        try {
            return CouponType.valueOf(name.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new RestBusinessException(EnumValueErrorCode.COUPON_TYPE_VALUE_INVALID);
        }
    }
}
