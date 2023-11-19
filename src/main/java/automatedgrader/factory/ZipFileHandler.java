package project.factory;

//import java.io.File;
import java.io.FileInputStream;
import java.nio.file.*;
import java.io.IOException;
//import java.nio.file.FileSystem;
//import java.nio.file.FileSystems;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFileHandler implements FileHandler{
    
    @Override
    public void handleFile(String filePath){
        try {
            //Directory where the zip file will be extracted
            String extractionPath = "files/extractedFiles";
            
            createDirectory(extractionPath);

            extractZipFile(filePath, extractionPath);
            
            processExtractedFiles(extractionPath);
            
            System.out.println("Zip file accepted, extracted and processed successfully.");
        } catch (IOException e) {
            System.err.println("IOException during handling the file: " + e.getMessage());
        } 
    }

    private void createDirectory(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);

        if (!Files.exists(path)){
            Files.createDirectory(path);
        }
    }

    private void extractZipFile(String zipFilePath, String extractionPath) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;

            while ((entry = zipInputStream.getNextEntry()) != null) {
                Path entryPath = Paths.get(extractionPath, entry.getName());

                //Create directories if they don't exist
                createDirectory(entryPath.getParent().toString());

                //Extract the entry
                Files.copy(zipInputStream, entryPath, StandardCopyOption.REPLACE_EXISTING);
                zipInputStream.closeEntry();
            }
        }            
    }

    private void processExtractedFiles(String extractionPath) throws IOException {
        //get all the files in extractedFiles
        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(extractionPath))){
            for (Path filePath : directoryStream){
                //Check if file is a zip file
                if(Files.isRegularFile(filePath) && filePath.toString().endsWith(".zip")) {
                    //Recursively handle the nested zip files
                    handleNestedZipFile(filePath.toString(), extractionPath);
                }
                else if (Files.isRegularFile(filePath) && filePath.toString().endsWith(".java")) {
                    //Process Java Files
                    System.out.println("Processing Java file: " + filePath.getFileName());
                }
            }
        }

        //Add other logic for process here
    }

    private void handleNestedZipFile(String nestedZipFilePath, String extractionPath) throws IOException {
        //Create a subdirectoru for the nested zip file
        String nestedZipDirectoryName = getFileNameWithoutExtension(nestedZipFilePath);
        String nestedZipDirectoryPath = Paths.get(extractionPath, nestedZipDirectoryName).toString();
        createDirectory(nestedZipDirectoryPath);

        //Extract the contents of the nested zip file into the subdirectory 
        extractZipFile(nestedZipFilePath, nestedZipDirectoryPath);
        
        //Process the extracted the files from the nested zip file
        processExtractedFiles(nestedZipDirectoryPath);
    }

    private String getFileNameWithoutExtension(String filePath){
        String fileName = Paths.get(filePath).getFileName().toString();
        int lastDotIndex = fileName.lastIndexOf(".");

        if(lastDotIndex != -1){
            return fileName.substring(0, lastDotIndex);
        }
        else {
            return fileName;
        }
    }
}