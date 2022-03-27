package mike.bootstrap.utilities.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ 
    ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { PasswordValidator.class })
public @interface Password {

    int min() default 8;

    int max() default 20;

    String message() default "password does not fit security rules";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
