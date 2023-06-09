package webling.coffee.backend.global.responses.errors.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MenuErrorCode implements ErrorCode{

    DUPLICATION(HttpStatus.BAD_REQUEST, "M001", "중복되는 메뉴가 존재합니다."),

    NOT_FOUND(HttpStatus.NOT_FOUND, "M002", "해당 메뉴를 찾을 수 없습니다."),

    NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "M003", "해당 메뉴는 현재 판매 가능하지 않습니다."),

    ALREADY_AVAILABLE(HttpStatus.BAD_REQUEST, "M004", "해당 메뉴는 이미 판매 가능 상태입니다."),

    COLD_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "M005", "해당 메뉴는 COLD 로 주문이 불가능합니다."),

    HOT_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "M006", "해당 메뉴는 HOT 으로 주문이 불가능합니다."),

    PHOTO_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "M007", "해당 메뉴의 사진 등록이 실패했습니다.");

    private final HttpStatus httpStatus;

    private final String errorCode;

    private final String message;
}
