package webling.coffee.backend.global.responses.errors.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import webling.coffee.backend.global.responses.errors.codes.ErrorCode;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private int status;

    private String error;

    private String code;

    private String message;

    private Map<String, String> errors;

    private String path;

    private ErrorResponse(final @NotNull ErrorCode errorCode,
                          final @NotBlank String requestURI) {
        this.status = errorCode.getHttpStatus().value();
        this.error = errorCode.getHttpStatus().getReasonPhrase();
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
        this.path = requestURI;
    }

    private ErrorResponse(final @NotEmpty List<FieldError> errors,
                          final @NotBlank String requestURI) {
        this.status = HttpStatus.BAD_REQUEST.value();
        this.error = HttpStatus.BAD_REQUEST.getReasonPhrase();
        this.path = requestURI;
        this.errors = errors.stream()
                .filter(fieldError -> fieldError.getDefaultMessage() != null)
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }

    private ErrorResponse(final @NotEmpty Set<ConstraintViolation<?>> errors,
                          final @NotBlank String requestURI) {
        this.status = HttpStatus.BAD_REQUEST.value();
        this.error = HttpStatus.BAD_REQUEST.getReasonPhrase();
        this.path = requestURI;
        this.errors = errors.stream()
                .collect(Collectors.toMap(e -> String.valueOf(e.getPropertyPath()), ConstraintViolation::getMessage));
    }


    private ErrorResponse(final @NotNull MaxUploadSizeExceededException errors,
                          final @NotBlank String requestURI) {
        this.status = HttpStatus.BAD_REQUEST.value();
        this.error = HttpStatus.BAD_REQUEST.getReasonPhrase();
        this.path = requestURI;
        this.message = errors.getMessage();

    }


    public static ResponseEntity<ErrorResponse> toResponseEntity(final @NotNull ErrorCode errorCode,
                                                                 final @NotBlank String requestURI) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode, requestURI));
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(final @NotEmpty List<FieldError> errors,
                                                                 final @NotBlank String requestURI) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(errors, requestURI));
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(final @NotEmpty Set<ConstraintViolation<?>> errors,
                                                                 final @NotBlank String requestURI) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(errors, requestURI));
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(final @NotNull MaxUploadSizeExceededException errors,
                                                                 final @NotBlank String requestURI) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(errors, requestURI));
    }
}
