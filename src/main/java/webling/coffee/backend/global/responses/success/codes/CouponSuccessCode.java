package webling.coffee.backend.global.responses.success.codes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CouponSuccessCode implements SuccessCode {

    CREATE (HttpStatus.CREATED, "CS001", "쿠폰 등록에 성공했습니다."),
    FIND(HttpStatus.OK, "CS002", "쿠폰 조회에 성공했습니다.");

    private final HttpStatus httpStatus;

    private final String successCode;

    private final String message;
}
