package mike.bootstrap.utilities.helpers;

import java.util.regex.Pattern;

public class StringValue {
    
    private String value;
    
    /**
     * Create a new String value instance.
     * 
     * @param value    original {@code String} (may be null)
     * @param defValue default value to set if the origin value is null, empty or blank (default is an empty String)
     * @return
     */
    public static StringValue of(String value, String... defValue) {
	return new StringValue(value, defValue);
    }
    
    /**
     * Constructor.
     * 
     * @see StringValue#of(String, String...)
     */
    private StringValue(String value, String... defValue) {
	this.value = Strings.nullAs(value, defValue);
    }
    
    /**
     * @return The resulting {@code String}
     */
    public String value() {
	return this.value;
    }
    
    /**
     * Remove all leading and trailing white space.
     * <pre>
     * StringValue.strip("  My   Value    ") => "My   Value"
     * </pre>
     * 
     * @return string value instance
     * @see Strings#strip()
     */
    public StringValue strip() {
	this.value = Strings.strip(this.value);
	return this;
    }
    
    /**
     * Set default value (default: empty) if null, empty or blank.
     * <pre>
     * StringValue.blankAs() => ""
     * StringValue.blankAs("foo") => "foo"
     * </pre>
     * 
     * @param defValue The default value to set
     * @return string value instance
     * @see Strings#blankAs(String, String...)
     */
    public StringValue blankAs(String... defValue) {
	this.value = Strings.blankAs(value, defValue);
	return this;
    }
    
    /**
     * Removes all newline(s) from a String
     * 
     * @return string value instance
     * @see Strings#chomp(String)
     */
    public StringValue chomp() {
	this.value = Strings.chomp(this.value);
	return this;
    }
    
    /**
     * Remove with all white space.
     * 
     * <pre>
     * StringValue.shrink("  My   Value    ") => "MyValue"
     * </pre>
     * 
     * @return string value instance
     * @see Strings#shrink(String)
     */
    public StringValue shrink() {
	this.value = Strings.shrink(this.value);
	return this;
    }
    
    /**
     * Replace all repeating spaces with a single space. 
     * Call {@link StringValue#strip()} first if the leading and trailing must be removed.
     *  
     * <pre>
     * StringValue.sanitize("  My   Value    ")  => "  My Value    "
     * </pre>
     * 
     * @return string value instance
     * @see Strings#sanitize(String)
     */
    public StringValue sanitize() {
	this.value = Strings.sanitize(this.value);
	return this;
    }
    
    /**
     * Replace all repeating spaces with the replacement {@code String}. 
     * 
     * @param replacement The replacement string
     * @return string value instance
     * @see StringValue#sanitize(Pattern, String)
     */
    public StringValue sanitize(String replacement) {
	this.value = Strings.sanitize(this.value, Strings.REGEX_SPACES, replacement);
	return this;
    }
    
    /**
     * Replaces every subsequence of the input sequence that matches the pattern with the given replacement string.
     * 
     * @param pattern The matching pattern
     * @param replacement The replacement string (default: single white space)
     * @return string value instance
     * @see Strings#sanitize(String, Pattern, String...)
     */
    public StringValue sanitize(Pattern pattern, String... replacement) {
	this.value = Strings.sanitize(this.value, pattern, replacement);
	return this;
    }
}
