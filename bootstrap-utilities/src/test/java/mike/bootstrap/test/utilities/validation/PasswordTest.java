package mike.bootstrap.test.utilities.validation;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import mike.bootstrap.utilities.validation.PasswordValidator;

@DisplayName("Validation::Password")
class PasswordTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { 
	    "             ", "6Char!", "Contains-More-Than-20-Characters", " C0ntains Sp@ces ",
	    " St@rtW1thSpace", "3nd-With-space ", "OnlyCharacters", "No-Numbers!", "0123457689", "$0123457689",
	    "GraterThan->Operat0r!" })
    void should_return_false_when_password_not_valid(String password) {

	var validator = new PasswordValidator();
	var valid = validator.isValid(password, null);

	assertThat(valid).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = { "My-S3cr3t", "12@Secured$" })
    void should_return_true_when_valid_password_is_valid(String password) {

	var validator = new PasswordValidator();
	var valid = validator.isValid(password, null);

	assertThat(valid).isTrue();
    }
}
