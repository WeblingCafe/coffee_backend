package webling.coffee.backend.global.responses.errors.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MenuErrorCode implements ErrorCode{

    DUPLICATION(HttpStatus.BAD_REQUEST, "M001", "중복되는 메뉴가 존재합니다."),

    NOT_FOUND(HttpStatus.NOT_FOUND, "M002", "해당 메뉴를 찾을 수 없습니다."),

    NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "M003", "해당 메뉴는 현재 판매 가능하지 않습니다."),

    ;

    private final HttpStatus httpStatus;

    private final String errorCode;

    private final String message;
}
