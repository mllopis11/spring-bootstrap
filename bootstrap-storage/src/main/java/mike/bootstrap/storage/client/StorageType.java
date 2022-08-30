package mike.bootstrap.storage.client;


public enum StorageType {

    FS("file-system"), S3("aws-s3");
    
    private final String label;

    private StorageType(String label) {
        this.label = label;
    }

    public String label() {
        return this.label;
    }
    
    public boolean isAwsS3() {
        return this == S3;
    }
    
    public boolean isLocal() {
        return this == FS;
    }
}
