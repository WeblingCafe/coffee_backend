package webling.coffee.backend.global.errors.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import webling.coffee.backend.global.errors.codes.CommonErrorCode;
import webling.coffee.backend.global.errors.codes.UserErrorCode;
import webling.coffee.backend.global.errors.exceptions.RestBusinessException;
import webling.coffee.backend.global.errors.response.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class RestControllerHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException (final @NotNull MethodArgumentNotValidException e,
                                                                                final @NotNull HttpServletRequest request) {

        log.error("Handle ['method argument not valid exception'] - code: '{}', message: '{}'", CommonErrorCode.BAD_REQUEST.getErrorCode(), e.getMessage());

        return ErrorResponse.toResponseEntity(e.getBindingResult().getFieldErrors(), request.getRequestURI());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException (final @NotNull HttpMessageNotReadableException e,
                                                                                final @NotNull HttpServletRequest request) {

        log.error("Handle ['method argument not valid exception'] - code: '{}', message: '{}'", CommonErrorCode.BAD_REQUEST.getErrorCode(), e.getMessage());

        return ErrorResponse.toResponseEntity(UserErrorCode.ENUM_VALUE_INVALID, request.getRequestURI());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(final @NotNull ConstraintViolationException e,
                                                                            final @NotNull HttpServletRequest request) {

        log.error("Handle ['constraint violation exception'] - code: '{}', message: '{}'",  CommonErrorCode.BAD_REQUEST.getErrorCode(), e.getMessage());

        return ErrorResponse.toResponseEntity(e.getConstraintViolations(), request.getRequestURI());
    }

    @ExceptionHandler(RestBusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(final @NotNull RestBusinessException e,
                                                                 final @NotNull HttpServletRequest request) {

        log.error("Handle ['business exception'] - code: '{}', message: '{}'", e.getErrorCode().getErrorCode(), e.getErrorCode().getMessage());

        return ErrorResponse.toResponseEntity(e.getErrorCode(), request.getRequestURI());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(final @NotNull MaxUploadSizeExceededException e,
                                                                                 final @NotNull HttpServletRequest request) {
        log.error("Handle ['business exception'] - code: '{}', message: '{}'", e.getMaxUploadSize(), e.getMessage());

        return ErrorResponse.toResponseEntity(e, request.getRequestURI());
    }
}

