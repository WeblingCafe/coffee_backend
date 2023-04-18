package webling.coffee.backend.global.errors.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MenuErrorCode implements ErrorCode{

    DUPLICATION(HttpStatus.BAD_REQUEST, "M001", "중복되는 메뉴가 존재합니다."),

    ;

    private final HttpStatus httpStatus;

    private final String errorCode;

    private final String message;
}
