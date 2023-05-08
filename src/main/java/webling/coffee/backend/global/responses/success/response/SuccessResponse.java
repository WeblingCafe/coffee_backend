package webling.coffee.backend.global.responses.success.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import webling.coffee.backend.global.responses.success.codes.SuccessCode;

@Getter
@Slf4j
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse {

    private int status;
    private String success;
    private String code;
    private String successMessage;
    private Object responseObject;

    private SuccessResponse(final @NotNull SuccessCode successCode, Object responseObject) {
        this.status = successCode.getHttpStatus().value();
        this.success = successCode.getHttpStatus().getReasonPhrase();
        this.code = successCode.getSuccessCode();
        this.successMessage = successCode.getMessage();
        this.responseObject = responseObject;
    }


    public static ResponseEntity<SuccessResponse> toResponseEntity (final @NotNull SuccessCode successCode,
                                                                    final @NotNull Object responseObject) {

        log.info("Success Response - success code : {}, success message : {}",
                successCode.getSuccessCode(), successCode.getMessage());

        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(new SuccessResponse(successCode, responseObject));
    }

    public static ResponseEntity<SuccessResponse> toResponseEntity (final @NotNull HttpHeaders headers,
                                                                    final @NotNull SuccessCode successCode,
                                                                    final @NotNull Object responseObject) {

        log.info("Success Response - success code : {}, success message : {}",
                successCode.getSuccessCode(), successCode.getMessage());

        return ResponseEntity
                .status(successCode.getHttpStatus())
                .headers(headers)
                .body(new SuccessResponse(successCode, responseObject));
    }
}
