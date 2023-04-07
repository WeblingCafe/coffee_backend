package webling.coffee.backend.global.errors.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode{

    USER_NOT_FOUND (HttpStatus.NOT_FOUND, "U001", "해당 유저를 찾을 수 없습니다."),
    USER_DUPLICATION (HttpStatus.BAD_REQUEST, "U002", "중복된 유저 입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "U003", "비밀번호가 일치하지 않습니다.");

    private final HttpStatus httpStatus;

    private final String errorCode;

    private final String message;
}