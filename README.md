# Bootstrap for Java Projects

Utility library for the most common operations used in java projects.

Components | Versions
---------- | --------
[JDK](https://adoptopenjdk.net) | 15+
[Maven](https://maven.apache.org) | 3.6.3 or higher
[GIT for Windows](https://gitforwindows.org) | Git Tooling (bash on windows)

**Notes:** 
- Prefer installation thru the archive (ZIP or Tarball) instead of Windows installer. By this way, you can control where the software are installed and do not pollute the windows registry.
- See the Software installation paragraph later in this Readme.

Projects | Descriptions
-------- | ------------
[bootstrap-dependencies](./bootstrap-dependencies) | Dependencies management versions
[bootstrap-parent](./bootstrap-parent) | Bootstrap Parent POM 

## Maven

### Local repository installation

```bash
$ git clone https://github.com/mllopis11/java-bootstrap.git [--branch <branchname>]
$ cd java-bootstrap
$ mvn clean install
```

### Release (Batch Mode)

1) Set the next development version if needed

```bash
$ # Set the next development version if needed (do not set for auto versioning)
$ nextSnapshotVersion=2.0-SNAPSHOT
```

2) (Optional) Execute dry-run to verify the release then clean

```bash
$ mvn -B release:prepare -DdryRun=true [-DdevelopmentVersion=${nextSnapshotVersion}]
# Manual check
$ mvn release:clean
```

3) Perform the Release

```bash
$ mvn -B release:prepare [-DdevelopmentVersion=${nextSnapshotVersion}]
$ mvn -B release:perform
```

