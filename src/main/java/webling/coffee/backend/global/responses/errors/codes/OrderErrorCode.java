package webling.coffee.backend.global.responses.errors.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode{

    NOT_FOUND (HttpStatus.NOT_FOUND, "O001", "해당 주문 정보를 찾을 수 없습니다."),
    CART_NOT_FOUNT (HttpStatus.NOT_FOUND, "O001", "해당 장바구니 정보를 찾을 수 없습니다."),

    ;

    private final HttpStatus httpStatus;

    private final String errorCode;

    private final String message;
}