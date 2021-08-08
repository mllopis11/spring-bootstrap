package mike.bootstrap.utilities.nio.files;

import java.nio.file.Path;
import java.util.stream.Stream;

import mike.bootstrap.utilities.system.SysInfo;

/**
 * Shortcuts and helpers on Path manipulation 
 * 
 * @author Mike (2021-02)
 * @since 11
 *
 */
public class PathUtils {

private PathUtils() {}
	
	/**
     * @param  path  filename
     * @return the directory of the file or an empty String if no directory is present.
     */
    public static String dirname(final String path) {
        return PathUtils.dirname(Path.of(path));
    }

    /**
     * @param  path  filename
     * @return the directory of the file or an empty String if no directory is present.
     */
    public static String dirname(final Path path) {
        var dir = path.getParent();
        return dir != null ?  dir.toString() : "";
    }

    /**
     * @param  path  filename
     * @return the basic filename (without the directory)
     */
    public static String basename(final String path) {
        return PathUtils.basename(Path.of(path));
    }

    /**
     * @param  path  filename
     * @return the basic filename (without the directory)
     */
    public static String basename(final Path path) {
        return path.getFileName().toString();
    }
    
    /**
     * Converts a path string and a sequence of strings to a Path.
     * 
     * @param  first  the path string or initial part of the path string
     * @param  more   additional strings to be joined
     * @return the resulting Path
     * @see    java.nio.file.Paths#get(String, String...)
     */
    public static Path toPath(String first, String... more) { 
        return Path.of(first, more);
    }

    /**
     * Converts a path and a sequence of strings to a Path.
     * 
     * @param  first  the path or initial part of the path
     * @param  more   additional strings to be joined
     * @return the resulting Path
     * @see    PathUtils#toPath(String, String...)
     */
    public static Path toPath(Path first, String... more) {
        return PathUtils.toPath(first.toString(), more);
    }

    /**
     * Converts a path and a sequence of path to a Path.
     * 
     * @param  first  the path or initial part of the path
     * @param  more   additional paths to be joined
     * @return the resulting Path
     * @see    PathUtils#toPath(String, Path...)
     */
    public static Path toPath(Path first, Path... more) {
        return PathUtils.toPath(first.toString(), more);
    }

    /**
     * Converts a path and a sequence of path to a Path.
     * 
     * @param  first  the path string or initial part of the path string
     * @param  more  additional strings to be joined
     * @return the resulting Path
     * @see    PathUtils#toPath(String, String...)
     */
    public static Path toPath(String first, Path... more) {
        String[] others = Stream.of(more).map(Path::toString).toArray(String[]::new);
        return PathUtils.toPath(first, others);
    }
    
    /**
     * Substitute all backslashes with slash for a Windows path.<br>
     * The substitution is only done for Windows OS. In all cases, the result
     * is the String representation of the path 
     * 
     * @param path a path
     * @return the string representation of the path with slash.
     * @see Path#toString()
     */
    public static String toUnixPath(Path path) {
    	if ( SysInfo.isWindows() ) {
    		return path.toString().replaceAll("\\+", "/");
    	}
    	
    	return path.toString();
    }
}
