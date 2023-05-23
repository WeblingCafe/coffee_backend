package webling.coffee.backend.global.responses.success.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserSuccessCode implements SuccessCode{

    LOGOUT (HttpStatus.OK, "US001", "로그아웃 되었습니다."),
    UPDATE (HttpStatus.OK, "US002", "로그인한 유저 본인의 정보가 수정 되었습니다."),
    UPDATE_ROLE (HttpStatus.OK, "US003", "권한부여가 성공적으로 되었습니다."),
    FIND (HttpStatus.OK, "US004", "가입된 유저 조회에 성공했습니다."),
    DELETE (HttpStatus.OK, "US005", "유저 삭제 (비활성화)에 성공했습니다."),
    REGISTER(HttpStatus.CREATED, "US006", "회원가입에 성공했습니다.");

    private final HttpStatus httpStatus;

    private final String successCode;

    private final String message;
}
