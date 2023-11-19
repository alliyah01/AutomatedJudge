package automatedgrader.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipInputStream;

public abstract class FileHandler {
    protected abstract void extract(String zipFilePath) throws IOException;

    //method to handle file extraction
    public void handleFile(String zipFilePath){
        try{
            if(checkForErrors(zipFilePath) == false){
                createDestinationDirectory(zipFilePath);
                extract(zipFilePath);
            }
        }
        catch(IOException e){
            System.err.println("An error occured when trying to extract the zip file at '" + zipFilePath + "'.");
            e.printStackTrace();
        }
    }

    //returns the file extension of a given file
    protected static String getExtension(String filename){
        int index = filename.lastIndexOf('.');
        String extension = "";
        
        if (index > 0)
            extension = filename.substring(index+1);
            
        return extension;
    }

    //creates directory where extracted files would be stored
    protected void createDestinationDirectory(String zipFilePath) throws IOException{
        Path destDirectory = Paths.get(zipFilePath.substring(0, zipFilePath.length()-4));

        if (!Files.exists(destDirectory)) {
            File destDir = new File(zipFilePath.substring(0, zipFilePath.length()-4));
            if (!destDir.exists()) {
                destDir.mkdir();
             }
        }
    }

    //checks for the presence of various issues with the file to be extracted. return false if no errors are detected.
    protected boolean checkForErrors(String zipFilePath) throws IOException{
        boolean hasError = false;
        if(!getExtension(zipFilePath).equals("zip")){
            System.err.println("There is no zip file at '" + zipFilePath + "' to extract.");
            System.out.println(getExtension(zipFilePath));
            hasError = true;
        }
        ZipInputStream in = new ZipInputStream(new FileInputStream(zipFilePath));
        if(in.getNextEntry() == null){
            System.err.println("The zip file at '" + zipFilePath + "' is empty.");
            hasError = true;
        }
        in.close();
        return hasError;
    }
}