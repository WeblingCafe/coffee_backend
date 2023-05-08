package webling.coffee.backend.global.responses.success.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthSuccessCode implements SuccessCode {

    LOGIN (HttpStatus.OK, "AS001", "로그인에 성공했습니다."),
    REGISTER(HttpStatus.CREATED, "AS002", "회원가입에 성공했습니다.");


    private final HttpStatus httpStatus;

    private final String successCode;

    private final String message;
}
