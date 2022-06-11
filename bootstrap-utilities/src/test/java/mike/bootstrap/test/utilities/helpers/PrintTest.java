package mike.bootstrap.test.utilities.helpers;

import static org.assertj.core.api.Assertions.*;

import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mike.bootstrap.utilities.helpers.Print;

@DisplayName("Helpers::print")
class PrintTest {

    private static final PrintStream consoleEnabled = System.out;
    
    @BeforeAll
    static void init() {
	// Disable console
	System.setOut(new PrintStream(OutputStream.nullOutputStream()));
    }
    
    @AfterAll
    static void tearDown() {
	System.setOut(consoleEnabled);
	Print.info("PrintTest completed");
    }
    
    @Test
    void should_display_console_message_without_timestamp() {
	
	var foo = new Foo();
	
	assertThatCode(() -> {
	    Print.timestamp(false);
	    Print.info(null);
	    Print.info(foo);
	    Print.info("Information message: %s", foo.getMessage());
	    Print.warn(foo);
	    Print.warn("Warning message: %s", foo.getMessage());
	    Print.error(foo);
	    Print.error("Error message: %s", foo.getMessage());
	    Print.fatal(foo);
	    Print.fatal("Fatal message: %s", foo.getMessage());
	})
	.doesNotThrowAnyException();
    }
    
    @Test
    void should_display_console_message_with_timestamp() {
	
	var foo = new Foo();
	
	assertThatCode(() -> {
	    Print.timestamp("HH:mm:ss");
	    Print.info(null);
	    Print.info(foo);
	    Print.info("Information message: %s", foo.getMessage());
	})
	.doesNotThrowAnyException();
    }
 
    private static class Foo {

	private final String message = "I'am Foo !";

	public String getMessage() {
	    return message;
	}

	@Override
	public String toString() {
	    return String.format("Foo [name=%s, package=%s]", getClass().getSimpleName(), getClass().getPackageName());
	}
    }
}
