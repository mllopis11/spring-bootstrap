package mike.bootstrap.utilities.validation;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Password validation constraints:
 * 
 * <pre>
 * ^                  start-of-string
 * (?=.*[0-9])        a digit at least once
 * (?=.*[a-z])        a lower case letter at least once
 * (?=.*[A-Z])        an upper case letter at least once
 * (?=.*[!?@#$%&\\-]) a special character at least once
 * (?=\S+$)           no whitespace allowed in the entire string
 * [...]{8,20}        authorized chararcters from 8 to 20
 * </pre>
 * 
 * @author Mike (2020-01)
 *
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final Pattern regex = Pattern.compile(
            "^(?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])(?=.*[!?@#$%&\\-])(?=\\S+$)[0-9a-zA-Z!?@#$%&\\-]{8,20}");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null ? regex.matcher(value).matches() : Boolean.FALSE;
    }

}
