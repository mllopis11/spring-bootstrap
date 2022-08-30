package mike.bootstrap.storage.client;

import mike.bootstrap.utilities.helpers.PreConditions;
import software.amazon.awssdk.regions.Region;

/**
 * @param name         client name (required)
 * @param tag          client tag (used to identify the client like the environment for example)
 * @param type         storage type
 * @param endpoint     server address (S3 only)
 * @param region       signin region (S3 only)
 * @param accessKey    S3 access key (S3 only)
 * @param secretKey    S3 secret key (S3 only)
 * @param baseFolder   file-system storage base folder (file-system type only)
 * @param bucketPrefix bucket prefix link to this client
 * 
 * @author Mike
 *
 */
public record StorageClientProperties(
        String name, String tag, StorageType type, String endpoint, Region region,
        String accessKey, String secretKey, String baseFolder, String bucketPrefix) {
    
    public StorageClientProperties {
        PreConditions.notBlank(name, "client name not set");
        
        if (type.isAwsS3()) {
            PreConditions.notBlank(endpoint, "client endpoint (address) not set");
            PreConditions.notBlank(accessKey, "client access key not set");
            PreConditions.notBlank(secretKey, "client secret key not set");
        } else if (type.isLocal()) {
            PreConditions.notBlank(baseFolder, "client base folder not set");
        }
    }
    
    @Override
    public String toString() {
        return String.format(
                "StorageClientProperties [name=%s, tag=%s, type=%s, endpoint=%s, region=%s, baseFolder=%s, bucketPrefix=%s]",
                name, tag, type, endpoint, region, baseFolder, bucketPrefix);
    }
}
