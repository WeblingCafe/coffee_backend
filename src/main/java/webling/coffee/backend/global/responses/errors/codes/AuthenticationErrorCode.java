package webling.coffee.backend.global.responses.errors.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthenticationErrorCode implements ErrorCode{

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A001", "유효하지 않은 토큰입니다."),

    ACCESS_DENIED(HttpStatus.FORBIDDEN, "A002", "접근 권한이 없습니다.")


    ;

    private final HttpStatus httpStatus;

    private final String errorCode;

    private final String message;

}
