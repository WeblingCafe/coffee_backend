package webling.coffee.backend.global.responses.errors.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum EnumValueErrorCode implements ErrorCode{

    USER_ROLE_VALUE_INVALID(HttpStatus.BAD_REQUEST, "E001", "유효하지 않은 유저 역할 입니다."),
    TEAM_VALUE_INVALID(HttpStatus.BAD_REQUEST, "E002", "유효하지 않은 팀 이름 값 입니다."),
    COUPON_TYPE_VALUE_INVALID(HttpStatus.BAD_REQUEST, "E003", "유효하지 않은 쿠폰타입 입니다."),
    BOARD_CATEGORY_VALUE_INVALID(HttpStatus.BAD_REQUEST, "E004", "유요하지 않은 게시판 카테고리 입니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
