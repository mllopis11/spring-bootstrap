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
$ git clone https://github.com/mllopis11/java-bootstrap.git
$ cd java-bootstrap
$ mvn clean install
```

### Release (Batch Mode)

```bash
$ # Set the next development version if needed (do not set for auto versioning)
$ nextSnapshotVersion=2.0-SNAPSHOT
$ relPrepare="mvn -B release:prepare"
$ [ ! -z ${nextSnapshotVersion} ] && relPrepare="${relPrepare} -DdevelopmentVersion=${nextSnapshotVersion}"
$ $(relPrepare) || exit 1
$
$ # Perform the release
$ mvn -B release:perform
```


## Software Installation

_ToDo_
