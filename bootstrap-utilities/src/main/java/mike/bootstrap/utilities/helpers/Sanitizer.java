package mike.bootstrap.utilities.helpers;

import java.util.function.BiFunction;

/**
 * Sanitize a String value.
 * <p>
 * Remove with all leading and trailing white space then replace remaining white
 * space(s) with the specified character and finally apply the function on the
 * value.
 * 
 * @author Mike (2021-02)
 *
 */
public enum Sanitizer implements BiFunction<String, Character, String> {

    LOWERCASE((v, u) -> whitespace(v, u).toLowerCase()), 
    UPPERCASE((v, u) -> whitespace(v, u).toUpperCase()),
    NOOP(Sanitizer::whitespace);

    private final BiFunction<String, Character, String> fn;

    private Sanitizer(BiFunction<String, Character, String> fn) {
	this.fn = fn;
    }

    @Override
    public String apply(String value, Character replacement) {
	return fn.apply(value, replacement);
    }

    private static String whitespace(String value, Character replacement) {
	String rc = replacement != null ? String.valueOf(replacement) : "";
	return value.strip().replaceAll("\\s+", rc);
    }
}
