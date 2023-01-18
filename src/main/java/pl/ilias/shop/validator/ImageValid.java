package pl.ilias.shop.validator;

import pl.ilias.shop.validator.impl.ImageValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageValid {
    String message() default "Not image extension";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
