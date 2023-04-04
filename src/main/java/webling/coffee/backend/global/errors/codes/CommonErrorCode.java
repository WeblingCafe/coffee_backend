package webling.coffee.backend.global.errors.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{

    BAD_REQUEST (HttpStatus.BAD_REQUEST, "C001", "common error - bad request"),
    INTERNAL_SERVER_ERROR (HttpStatus.INTERNAL_SERVER_ERROR, "C002", "common error - internal server error"),
    NOT_FOUND (HttpStatus.NOT_FOUND, "C003", "common error - not found"),

    ;

    private final HttpStatus httpStatus;

    private final String errorCode;

    private final String message;
}
