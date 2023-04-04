package webling.coffee.backend.global.errors.codes;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String name();
    HttpStatus getHttpStatus();
    String getErrorCode();
    String getMessage();

}
