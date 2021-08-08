package mike.bootstrap.test.utilities.helpers;

import static org.assertj.core.api.Assertions.*;

import java.nio.file.Path;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import mike.bootstrap.utilities.helpers.Utils;
import mike.bootstrap.utilities.nio.files.PathUtils;

@DisplayName("Helpers::PathUtils")
class PathUtilsTest {

    @ParameterizedTest
    @CsvSource( { 
        "./root_folder/test/my_file.txt, ./root_folder/test",
        "./my_file.txt, .", 
        "my_file.txt, "} )
    void should_return_directory_name_when_filename(String fullFilename, String expectedDirname) {
        
        Path expectedDirPath = Path.of(Utils.trim(expectedDirname));
        
        String dirname = PathUtils.dirname(fullFilename);
        
        assertThat(dirname).isEqualTo(expectedDirPath.toString());
    }
    
    @ParameterizedTest
    @CsvSource( { 
        "./root_folder/test/my_file.txt, my_file.txt",
        "my_file.txt, my_file.txt"} )
    void should_return_basename_when_filename(String fullFilename, String expectedFilename) {
        
        String filename = PathUtils.basename(fullFilename);
        
        assertThat(filename).isEqualTo(expectedFilename);
    }
    
    @ParameterizedTest
    @CsvSource( { 
        ".:root_folder:test:my_file.txt, ./root_folder/test/my_file.txt",
        "my_file.txt, my_file.txt" } )
    void should_return_path_when_toPath(String pathItems, String expectedPathname) {
        
        String[] paths = pathItems.split(":");
        String first = paths[0];
        String[] more = paths.length >= 2 ? Arrays.copyOfRange(paths, 1, paths.length) : new String[] {};
        
        Path path = PathUtils.toPath(first, more);
        Path expectedPath = Path.of(expectedPathname);
                
        assertThat(path).isEqualTo(expectedPath);
    }
}
