package webling.coffee.backend.global.annotation;

import webling.coffee.backend.global.enums.UserRole;
import static webling.coffee.backend.global.enums.UserRole.*;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthRequired {

    UserRole[] roles() default {MANAGER, EMPLOYEE, GUEST, DEVELOPER};

    boolean without() default false;
}
