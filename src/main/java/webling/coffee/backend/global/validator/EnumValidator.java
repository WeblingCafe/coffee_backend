package webling.coffee.backend.global.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import webling.coffee.backend.global.annotation.VerifyEnum;

public class EnumValidator implements ConstraintValidator<VerifyEnum, String> {
    private VerifyEnum verifyEnumAnnotation;

    @Override
    public void initialize(VerifyEnum constraintAnnotation) {
        this.verifyEnumAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Object[] enumValues = this.verifyEnumAnnotation.enumClass().getEnumConstants();
        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value.equals(enumValue.toString())
                        || (this.verifyEnumAnnotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))) {
                    return true;
                }
            }
        }
        return false;
    }
}
