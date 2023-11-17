package automatedgrader;

/**
 * Hello world!
 *
 */
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        FileExtractor fileExtractor = new FileExtractor();
        try {
            fileExtractor.unzip("files/submissions.zip", "files/submissions");
        }
        catch(Exception e){
            System.out.println("error");
        }
    }
}
