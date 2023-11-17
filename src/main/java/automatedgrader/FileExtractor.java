package automatedgrader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.ArrayList;

public class FileExtractor
{
    private static final int BUFFER_SIZE = 4096;
    public static ArrayList <String> files = new ArrayList <String>();

  
    public FileExtractor()
    {
        
    }
    
    
    public static void unzip(String zipFilePath, String destDirectory) throws IOException
    {
     File destDir = new  File(destDirectory);
     
     if (!destDir.exists()) {
            destDir.mkdir();
     }
     ZipInputStream in = new ZipInputStream(new FileInputStream(zipFilePath));
     ZipEntry entry = in.getNextEntry();
     
     while (entry != null) {
        String filePath = destDirectory + File.separator + entry.getName();
        if (!entry.isDirectory()  && getExtension(filePath).equals("java")) {
            // if the entry is a JAVA file, extract it
            extractJavaFile(in, filePath);
            files.add(filePath);
        }
        else if(getExtension(filePath).equals("zip")){
            File zipFile = new File(filePath);
            if(!zipFile.exists()){
                //save student zip files to submissions folder
                Path resolvedPath = Paths.get(filePath);
                Files.createDirectories(resolvedPath.getParent());
                Files.copy(in, resolvedPath);
                //recursive call to extract java files from each student submission zip
                unzip(filePath, filePath.substring(0, filePath.length()-4));
                //remove student zip file from submissions
                Files.deleteIfExists(resolvedPath);
            }
        }
        else if (entry.isDirectory()){
            // if the entry is a directory, make the directory
            File dir = new File(filePath);
            dir.mkdirs();
        }
            in.closeEntry();
            entry = in.getNextEntry();
        }
        in.close();
        
        for(String file: files)
         System.out.println(file);
    }

    //determine the file extension
    public static String getExtension(String filename){
        int index = filename.lastIndexOf('.');
        String extension = "";
        
        if (index > 0)
            extension = filename.substring(index+1);
            
        return extension;
    }
    

    private static void extractJavaFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) { //read zip file bytes 
            out.write(bytesIn, 0, read);  //write bytes to destination
        }
        out.close();
    }

    // private static void extractZipFile(ZipInputStream zipIn, String filePath) throws IOException {
    //     try {
    //         ZipEntry entry = zipIn.getNextEntry();
    //         while (entry != null) {
    //             Path resolvedPath = Paths.get(filePath).resolve(entry.getName()).normalize();
    //             if (!resolvedPath.startsWith(filePath)) {
    //                 throw new RuntimeException("Entry with an illegal path: " + entry.getName());
    //             }
    //             if (entry.isDirectory()) {
    //                 Files.createDirectories(resolvedPath);
    //             } 
    //             else {
    //                 Files.createDirectories(resolvedPath.getParent());
    //                 Files.copy(zipIn, resolvedPath);
    //             }
    //         }
    //     }
    //     catch(IOException e){

    //     }
    // }
}
