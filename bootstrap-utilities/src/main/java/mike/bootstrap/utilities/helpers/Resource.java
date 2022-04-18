package mike.bootstrap.utilities.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * Resource (Path, URL, URI) helper.
 * 
 * @author Mike (2021-02)
 * @Since 11
 */
public class Resource {

    private String target = null;
    private URL url = null;
    private boolean fileSystem = false;
    private boolean found = false;

    /**
     * Constructor.
     * 
     * @param resoure resource target location (FileSystem, Classpath or URL)
     */
    public Resource(String target) {

	this.target = Utils.strip(target);

	if (this.target.isEmpty()) {
	    throw new IllegalArgumentException("no such filename provided (blank)");
	}

	var path = Path.of(target);
	this.fileSystem = Files.isReadable(path);

	if (fileSystem) {
	    try {
		this.url = path.toUri().toURL();
		this.found = true;
	    } catch (MalformedURLException mue) {
		throw new IllegalArgumentException("convert target path to URL: " + target, mue);
	    }
	} else {
	    this.url = ClassLoader.getSystemResource(this.target);
	    this.found = this.url != null;
	}
    }

    /**
     * Constructor
     * 
     * @param file resource file
     */
    public Resource(Path file) {
	this(file != null ? file.toString() : "");
    }

    /**
     * @return true if the resource exists
     */
    public boolean found() {
	return this.found;
    }

    /**
     * @return true if the resource not exists
     */
    public boolean notFound() {
	return !this.found();
    }

    /**
     * @return the given target resource
     */
    public String getTarget() {
	return this.target;
    }

    /**
     * @return the resource URL or null if the resource does not exists
     */
    public URL getURL() {
	return this.url;
    }

    /**
     * @return the resource URI or null if the resource does not exists
     */
    public URI getURI() {
	try {
	    return this.url != null ? this.url.toURI() : null;
	} catch (URISyntaxException use) {
	    throw new IllegalArgumentException("convert URL to URI: " + this.url, use);
	}
    }

    /**
     * @return the resource input stream
     * @throws IOException if any IO errors occurs
     */
    public InputStream getInputStream() throws IOException {

	if (!this.found()) {
	    throw new IOException("resource does not exists: " + this.target);
	}

	if (fileSystem) {
	    return Files.newInputStream(Path.of(target));
	} else {
	    return url.openStream();
	}
    }

    /**
     * @return return all non blank lines with default charset UTF_8.
     * @throws IOException if any IO errors occurs
     */
    public List<String> getContent() throws IOException {
	return this.getContent(StandardCharsets.UTF_8);
    }

    /**
     * @return return all non blank lines
     * @throws IOException if any IO errors occurs
     */
    public List<String> getContent(Charset charset) throws IOException {

	try (var is = this.getInputStream();
		var isr = new InputStreamReader(is, charset);
		var reader = new BufferedReader(isr);
		Stream<String> lines = reader.lines().filter(l -> !l.isBlank())) {
	    return lines.toList();
	}
    }

    /**
     * @return resource as properties object. the resource must be a properties
     *         file.
     * @throws IOException if any IO errors occurs
     */
    public Properties getProperties() throws IOException {

	var properties = new Properties();

	if (this.found()) {
	    try (var is = this.getInputStream();) {
		properties.load(is);
	    }
	}

	return properties;
    }

    @Override
    public String toString() {
	return this.getTarget();
    }
}
