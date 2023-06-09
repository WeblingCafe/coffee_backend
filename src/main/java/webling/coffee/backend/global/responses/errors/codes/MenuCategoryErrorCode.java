package webling.coffee.backend.global.responses.errors.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MenuCategoryErrorCode implements ErrorCode{

    NOT_FOUND (HttpStatus.NOT_FOUND, "MC001", "해당하는 메뉴 카테고리를 찾을 수 없습니다."),
    DUPLICATION (HttpStatus.BAD_REQUEST, "MC002", "해당 메뉴 카테고리 이름이 이미 존재합니다.")

    ;

    private final HttpStatus httpStatus;

    private final String errorCode;

    private final String message;
}
