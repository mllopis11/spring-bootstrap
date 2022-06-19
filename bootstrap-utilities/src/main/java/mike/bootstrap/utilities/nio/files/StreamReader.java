package mike.bootstrap.utilities.nio.files;

import java.io.Closeable;
import java.io.IOException;
import java.util.stream.Stream;

public interface StreamReader extends Closeable {

    Stream<String> lines() throws IOException;
}
