package automatedgrader;

/*
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
*/
import automatedgrader.factory.FileHandler;
import automatedgrader.factory.FileHandlerFactory;
import automatedgrader.factory.ZipFileHandlerFactory;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class App {
    public static void main(String[] args) {
        //Code to accept and extract zipped files
        FileHandlerFactory fileHandlerFactory = new ZipFileHandlerFactory();
        FileHandler fileHandler = fileHandlerFactory.createFileHandler();

        String zipFilePath = "files/submissions.zip";
        fileHandler.handleFile(zipFilePath);
    }
        
        /*
        FileExtractor fileExtractor = new FileExtractor();
        try {
            fileExtractor.unzip("files/submissions.zip", "files/submissions");
        }
        catch(Exception e){
            System.out.println("error");
        }

        File submissions = new File("files/submissions");
        File[] submissionFolders = submissions.listFiles();
        ArrayList <File> submissionFiles = new ArrayList<>();

        for(File submissionFile: submissionFolders){
            submissionFiles.addAll(Arrays.asList(submissionFile.listFiles()));
        }


        for(File submissionFile: submissionFiles){
            //System.out.println(submissionFile);
            if(!submissionFile.isDirectory()){
                try {
                    // Specify the path to your Java file
                    FileInputStream fis = new FileInputStream(submissionFile);

                    // Parse the Java file using an instance of JavaParser
                    JavaParser javaParser = new JavaParser();
                    CompilationUnit cu = javaParser.parse(fis).getResult().orElse(null);

                    // Visit and print class information
                    if (cu != null) {
                        new ClassVisitor().visit(cu, null);
                    }

                    fis.close();
            }   
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
         }
         */
    
    

    // Visitor to print class information
    private static class ClassVisitor extends VoidVisitorAdapter<Void> {
    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        System.out.println("Class Name: " + n.getAccessSpecifier().asString() + " " + n.getName());
    
        // Print fields
        System.out.println("Fields:");
        n.getFields().forEach(field -> {
            System.out.println("- " + field.getAccessSpecifier().asString() + " " + field.getVariables().get(0).getNameAsString());
        });
    
        // Print methods
        System.out.println("Methods:");
        n.getMethods().forEach(method -> {
            System.out.println("- " + method.getAccessSpecifier().asString()  + " " +  method.getName());
        });
    
        super.visit(n, arg);
    }
    }
}
