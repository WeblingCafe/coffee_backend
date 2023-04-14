package webling.coffee.backend.global.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import webling.coffee.backend.global.validator.EnumValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {EnumValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifyEnum {
    String message() default "유효하지 않은 ENUM 값 입니다. 다시 확인해주세요.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends java.lang.Enum<?>> enumClass();
    boolean ignoreCase() default false;
}
