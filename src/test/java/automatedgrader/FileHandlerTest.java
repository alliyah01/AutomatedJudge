package automatedgrader;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import automatedgrader.template.NestedZipFileHandler;

public class FileHandlerTest extends TestCase{
    @Before
    public static void initializeState() throws Exception{
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream("student6.zip"));
        ZipEntry file1 = new ZipEntry("file1.java");
        out.putNextEntry(file1);
        out.close();
        out = new ZipOutputStream(new FileOutputStream("temp.zip"));
        ZipEntry student6zip = new ZipEntry("student6.zip");
        out.putNextEntry(student6zip);
        out.close();
    }

    @Test
    public void testNestedZipFileHandler() throws Exception{
        NestedZipFileHandler fileHandler = new NestedZipFileHandler();
        fileHandler.handleFile("temp.zip");
        File f = new File("temp");
        // assertTrue(f.isFile());
        // if(!sourceDirectory.exists()){
        //     sourceDirectory.mkdir();
        // }
        // try{
        
        // }
        // catch(Exception e){

        // // }
        // ZipEntry student1zip = new ZipEntry("student1.zip");
        // ZipEntry student2zip = new ZipEntry("student2.zip");
        // ZipEntry student3zip = new ZipEntry("student3.zip");
        // ZipEntry student4zip = new ZipEntry("student4.zip");
        // ZipEntry student5zip = new ZipEntry("student5.zip");
        
        // File file = new File("sampleFile1.java");
        // FileInputStream(file);
        // try{
        //     out.putNextEntry(student1zip);
        //     out.putNextEntry(student2zip);
        //     out.putNextEntry(student3zip);
        //     out.putNextEntry(student4zip);
        //     out.putNextEntry(student5zip);
        //     out.putNextEntry(student6zip);
        // // }
        // // catch(Exception e){

        // // }
        // fileHandler.handleFile("temp.zip");
        // try{
        //     Files.delete(Paths.get("temp.zip"));
        // }
        // catch(Exception e){

        // }
    }
    @AfterClass
    public static void clear() throws IOException{
        if(Files.deleteIfExists(Paths.get("temp.zip")))
            assertTrue(true);
        else
            assertTrue(false);
    }
}
