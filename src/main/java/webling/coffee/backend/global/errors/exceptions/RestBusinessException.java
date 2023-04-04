package webling.coffee.backend.global.errors.exceptions;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import webling.coffee.backend.global.errors.codes.ErrorCode;

@Getter
public class RestBusinessException extends RuntimeException{

    private final ErrorCode errorCode;

    public RestBusinessException (final @NotNull ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public static class NotFound extends RestBusinessException {
        public NotFound(ErrorCode errorCode) {
            super(errorCode);
        }
    }

    public static class Duplication extends RestBusinessException{
        public Duplication(ErrorCode errorCode) {
            super(errorCode);
        }
    }

    public static class Failure extends RestBusinessException {
        public Failure(ErrorCode errorCode) {
            super(errorCode);
        }
    }

    public static class OutOfBound extends RestBusinessException {
        public OutOfBound(ErrorCode errorCode) {
            super(errorCode);
        }
    }
}