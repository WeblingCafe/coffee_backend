package webling.coffee.backend.global.responses.errors.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardErrorCode implements ErrorCode{

    NOT_FOUND (HttpStatus.NOT_FOUND, "B001", "게시판 정보를 찾을 수 없습니다."),
    IS_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "B002", "해당 게시판은 비솰성화 상태입니다."),
    IS_AVAILABLE(HttpStatus.BAD_REQUEST, "B003", "해당 게시판은 이미 활성화 상태입니다. "),

    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}
