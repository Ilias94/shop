package pl.ilias.shop.validator;

import pl.ilias.shop.validator.impl.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValid {
    String message() default "Unmatched password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
