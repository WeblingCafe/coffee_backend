package webling.coffee.backend.global.responses.errors.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import webling.coffee.backend.global.responses.errors.codes.CommonErrorCode;
import webling.coffee.backend.global.responses.errors.exceptions.RestBusinessException;
import webling.coffee.backend.global.responses.errors.response.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class RestControllerHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException (final @NotNull MethodArgumentNotValidException e,
                                                                                final @NotNull HttpServletRequest request) {

        log.error("Handle ['method argument not valid exception'] - code: '{}', message: '{}'", CommonErrorCode.BAD_REQUEST.getErrorCode(), e.getMessage());

        return ErrorResponse.toResponseEntity(e.getBindingResult().getFieldErrors(), request.getRequestURI());
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

