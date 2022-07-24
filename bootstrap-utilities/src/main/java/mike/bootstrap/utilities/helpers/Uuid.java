package mike.bootstrap.utilities.helpers;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * UUID Generators.
 * 
 * @author Mike (2021-02)
 *
 */
public class Uuid {

    /**
     * Contructor (prevent any instantiation)
     */
    private Uuid() {}

    /**
     * Get UUID type 4 (Secure Random UUID).
     * <p>
     * The Java implementation is SecureRandom, which uses an unpredictable value as the seed to
     * generate random numbers to reduce the chance of collisions.
     * 
     * @return a random UUID.
     * @see UUID#randomUUID
     */
    public static String uuidV4() {
        return UUID.randomUUID().toString();
    }

    /**
     * Get short UUID type 4 (Secure Random UUID).
     * 
     * @return the first symbol of the random UUID
     */
    public static String uuidV4Short() {
        return Uuid.first(Uuid.uuidV4());
    }

    /**
     * Get stripped UUID type 4 (Secure Random UUID).
     * 
     * @return a random UUID without dash.
     */
    public static String uuidV4Stripped() {
        return Uuid.strip(Uuid.uuidV4());
    }

    /**
     * Get UUID type 3 base on the provided name with namespace.
     *
     * @param name source name (filename, object identifier ...)
     * @return the source name UUID.
     */
    public static String uuidV3(String name) {
        return Uuid.uuidV3("", name);
    }

    /**
     * Get UUID type 3 based on the provided namespace and source name.
     * <p>
     * This method will return the same UUID for the same name and namespace.
     * 
     * @param namespace a name space identifier (DNS, URL ...)
     * @param name      source name (filename, object identifier ...)
     * @return the source name UUID.
     */
    public static String uuidV3(String namespace, String name) {
        var source = namespace + name;
        var bytes = source.getBytes(StandardCharsets.UTF_8);
        return UUID.nameUUIDFromBytes(bytes).toString();
    }

    /**
     * Remove dash from the UUID.
     * 
     * @param uuid the UUID.
     * @return the UUID without dash.
     */
    public static String strip(String uuid) {
        return uuid.replace("-", "");
    }

    /**
     * Remove dash from the UUID.
     * 
     * @param uuid the UUID.
     * @return the first item of the UUID.
     */
    public static String first(String uuid) {
        return uuid.split("-", 2)[0];
    }
}
