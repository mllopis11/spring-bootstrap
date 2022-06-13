package mike.bootstrap.test.utilities.nio;

import static org.assertj.core.api.Assertions.*;

import java.nio.file.Path;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import mike.bootstrap.utilities.helpers.Strings;
import mike.bootstrap.utilities.nio.files.PathUtils;

@DisplayName("Nio::PathUtils")
class PathUtilsTest {

    @ParameterizedTest
    @CsvSource( { 
        "./root_folder/test/my_file.txt, ./root_folder/test",
        "./my_file.txt, .", 
        "my_file.txt, "} )
    void should_return_directory_name_when_filename(String fullFilename, String expectedDirname) {
        
        var expectedDirPath = Path.of(Strings.strip(expectedDirname));
        
        var dirname = PathUtils.dirname(fullFilename);
        
        assertThat(dirname).isEqualTo(expectedDirPath.toString());
    }
    
    @ParameterizedTest
    @CsvSource( { 
        "./root_folder/test/my_file.txt, my_file.txt",
        "my_file.txt, my_file.txt"} )
    void should_return_basename_when_filename(String fullFilename, String expectedFilename) {
        
        var filename = PathUtils.basename(fullFilename);
        
        assertThat(filename).isEqualTo(expectedFilename);
    }
    
    @ParameterizedTest
    @CsvSource( { 
        ".:root_folder:test:my_file.txt, ./root_folder/test/my_file.txt",
        "my_file.txt, my_file.txt" } )
    void should_return_path_when_toPath(String pathItems, String expectedPathname) {
        
        var paths = pathItems.split(":");
        var first = paths[0];
        var more = paths.length >= 2 ? Arrays.copyOfRange(paths, 1, paths.length) : new String[] {};
        
        var path = PathUtils.of(first, more);
        var expectedPath = Path.of(expectedPathname);
                
        assertThat(path).isEqualTo(expectedPath);
    }
    
    @Test
    void should_return_unix_path_when_windows_path() {
	var winPath = "C:\\user\\johndoe\\data";
	
	var unixPath = PathUtils.toUnixPath(Path.of(winPath));
	
	assertThat(unixPath).doesNotContain("\\");
    }
}
