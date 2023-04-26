package webling.coffee.backend.global.responses.errors.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CouponErrorCode implements ErrorCode{

    EXCEEDED_AMOUNT (HttpStatus.BAD_REQUEST, "CP001", "사용가능한 쿠폰 수량을 초과했습니다."),

    ;

    private final HttpStatus httpStatus;

    private final String errorCode;

    private final String message;
}
