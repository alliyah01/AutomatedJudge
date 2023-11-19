package automatedgrader;

/**
 * Hello world!
 *
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import automatedgrader.template.NestedZipFileHandler;

public class App {
    public static void main(String[] args) {
        //extracts all files from 'files/submissions.zip' and saves them to 'files/submissions'
        NestedZipFileHandler fileExtractor = new NestedZipFileHandler();
        fileExtractor.handleFile("files/submissions.zip");
    }
}
