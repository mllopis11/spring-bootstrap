package mike.bootstrap.utilities.helpers;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTransformer {

    private final Pattern pattern;
    
    public static StringTransformer of(Pattern pattern) {
	return new StringTransformer(pattern);
    }
    
    private StringTransformer(Pattern pattern) {
	PreConditions.notNull(pattern, "regexp pattern is required");
	this.pattern = pattern;
    }
    
    public RegexExpression expression(String basename, String expression) {
	return new RegexExpression(this.pattern, basename, expression);
    }
    
    public static class RegexExpression {
	
	private final String regexName;
	
	private String finalName;
	
	RegexExpression(Pattern pattern, String value, String expression) {
	    
	    PreConditions.notBlank(expression, "no such replacement expression");
	    
	    this.regexName = Optional.of(pattern.matcher(Strings.strip(value)))
			.filter(Matcher::matches)
			.map(m -> m.replaceAll(expression))
			.orElseThrow(() -> new IllegalArgumentException(
				String.format("value does not match pattern: %s (pattern: %s)", value, pattern.pattern())));
	    
	    this.finalName = regexName;
	}
	
	public RegexExpression replace(String key, String value) {
	    this.finalName = this.finalName.replace("%" + key+ "%", value);
	    return this;
	}
	
	public RegexExpression replace(Map<String, String> keys) {
	    keys.forEach(this::replace);
	    return this;
	}
	
	public String get() {
	    if (this.finalName.contains("%")) {
		throw new IllegalStateException(String.format("remaining unresolved key(s): %s", this.finalName));
	    }

	    return this.finalName;
	}
    }
}
