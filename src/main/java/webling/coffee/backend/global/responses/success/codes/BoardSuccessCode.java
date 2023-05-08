package webling.coffee.backend.global.responses.success.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardSuccessCode implements SuccessCode {

    CREATE (HttpStatus.CREATED, "BS001", "게시판 등록에 성공했습니다."),
    UPDATE(HttpStatus.OK, "BS002", "게시판 수정에 성공했습니다."),
    FIND_ALL_AVAILABLE_TRUE(HttpStatus.OK, "BS003", "활성화된 모든 게시판 조회에 성공했습니다."),
    FIND_AVAILABLE_TRUE(HttpStatus.OK, "BS004", "게시판 조회에 성공했습니다."),
    DISABLE(HttpStatus.OK, "BS005", "게시판 비활성화에 성공했습니다."),
    ENABLE(HttpStatus.OK, "BS006", "게시판 활성화에 성공했습니다."),

    ;

    private final HttpStatus httpStatus;

    private final String successCode;

    private final String message;
}
