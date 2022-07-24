package mike.bootstrap.utilities.nio.files;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;

import mike.bootstrap.utilities.helpers.PreConditions;
import mike.bootstrap.utilities.helpers.Strings;

/**
 * Resource (Path, URL, URI) helper.
 * 
 * @author Mike (2021-02)
 * @Since 11
 */
public class Resource {

    private final String name;
    private final URL url;
    private final boolean localResource;
    private final boolean exists;

    /**
     * @param file resource file path
     * @return Resource instance
     */
    public static Resource of(Path file) {
        var name = file != null ? file.toString() : Strings.EMPTY;
        return Resource.of(name);
    }

    /**
     * @param resoure resource name (FileSystem, Classpath or URL)
     * @return Resource instance
     */
    public static Resource of(String name) {
        return new Resource(name);
    }

    /**
     * Private Constructor.
     * 
     * @param resoure resource target location (FileSystem, Classpath or URL)
     */
    private Resource(String name) {

        this.name = PreConditions.notBlank(name, "no such resource provided").strip();

        var path = Path.of(this.name);
        this.localResource = Files.isReadable(path);

        if (localResource) {

            try {
                this.url = path.toUri().toURL();
                this.exists = true;
            } catch (MalformedURLException mue) {
                throw new IllegalArgumentException("convert target path to URL: " + name, mue);
            }
        } else {
            this.url = ClassLoader.getSystemResource(this.name);
            this.exists = this.url != null;
        }
    }

    /**
     * @return true if the resource exists
     */
    public boolean exists() {
        return this.exists;
    }

    /**
     * @return true if the resource not exists
     */
    public boolean notExists() {
        return !this.exists();
    }

    /**
     * @return the given target resource
     */
    public String getName() {
        return this.name;
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

        if (!this.exists()) {
            throw new FileNotFoundException("resource does not exists: " + this.name);
        }

        if (localResource) {
            return Files.newInputStream(Path.of(name));
        } else {
            return url.openStream();
        }
    }

    public StreamReader streamReader() {
        return this.streamReader(s -> true);
    }

    public StreamReader streamReader(Predicate<String> filter) {
        return new ResourceStreamReader(this, filter, StandardCharsets.ISO_8859_1);
    }

    public List<String> readContent() throws IOException {
        return this.readContent(s -> true);
    }

    public List<String> readContent(Predicate<String> filter) throws IOException {

        try (var reader = this.streamReader(filter)) {
            return reader.lines().toList();
        }
    }

    /**
     * @return resource as properties object. the resource must be a properties file.
     * @throws IOException if any IO errors occurs
     */
    public Properties getProperties() throws IOException {

        var properties = new Properties();

        if (this.exists()) {

            try (var is = this.getInputStream();) {
                properties.load(is);
            }
        }

        return properties;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
