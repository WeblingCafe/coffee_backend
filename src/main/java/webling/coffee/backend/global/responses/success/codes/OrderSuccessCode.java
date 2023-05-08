package webling.coffee.backend.global.responses.success.codes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderSuccessCode implements SuccessCode{

    CREATE(HttpStatus.CREATED, "OS001", "주문 생성에 성공했습니다."),
    ORDERED_ORDER_FIND(HttpStatus.OK, "OS002", "주문된 주문 리스트 조회에 성공했습니다."),
    ORDERED_ORDER_ME_FIND(HttpStatus.OK, "OS003", "로그인한 회원의 주문된 주문 리스트 조회에 성공했습니다."),
    CANCEL(HttpStatus.OK, "OS004", "주문 취소에 성공했습니다."),
    COMPLETED(HttpStatus.OK, "OS005", "주문을 완성하여 주문자 호출에 성공했습니다."),

    ;

    private final HttpStatus httpStatus;

    private final String successCode;

    private final String message;
}
