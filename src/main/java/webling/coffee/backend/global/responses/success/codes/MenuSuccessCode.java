package webling.coffee.backend.global.responses.success.codes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MenuSuccessCode implements SuccessCode {

    CREATE (HttpStatus.CREATED, "MS001", "메뉴 등록에 성공했습니다."),
    FIND(HttpStatus.OK, "MS002", "메뉴 조회에 성공했습니다."),
    UPDATE(HttpStatus.OK, "MS003", "메뉴 수정에 성공했습니다."),
    SOLD_OUT(HttpStatus.OK, "MS004", "해당 메뉴가 품절처리 되었습니다."),
    RESTORE(HttpStatus.OK, "MS005", "해당 메뉴가 판매 가능 처리 되었습니다."),
    FAVORITE(HttpStatus.OK, "MS006", "해당 메뉴가 즐겨찾기 등록 되었습니다."),
    NOT_FAVORITE(HttpStatus.OK, "MS007", "해당 메뉴가 즐겨찾기 해제 되었습니다."),
    FIND_FAVORITE(HttpStatus.OK, "MS008", "즐겨찾기 메뉴 리스트 조회에 성공했습니다."),
    CATEGORY_CREATE(HttpStatus.CREATED, "MS009", "메뉴 카테고리 등록에 성공했습니다."),
    CATEGORY_UPDATE(HttpStatus.OK, "MS010", "메뉴 카테고리 수정에 성공했습니다."),
    ;

    private final HttpStatus httpStatus;

    private final String successCode;

    private final String message;
}
