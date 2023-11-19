package automatedgrader.strategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class NamingConventionEvaluation implements EvaluationStrategy { //names to be updated some more
    @Override
    public void evaluate(String javaFilePath){
        try{
            //Extract class names from the Java file
            List<String>classNames = extractClassSignature(javaFilePath);

            //Check naming conventions for each class
            for (String className : classNames){
                if(!isValidClassName(className)){
                    System.out.println("Naming convention violation: " + className);
                }
            }
        } catch (IOException e){
            System.err.println("Error reading Java file:" + e.getMessage());
        }
    }

    private List<String> extractClassSignature(String javaFilePath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(javaFilePath)));
    
       String regex = "\\s*public\\s+class\\s+(\\w+)\\s*\\{?\\s*";
       List<String>classNames = new ArrayList<>();

       java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
       java.util.regex.Matcher matcher = pattern.matcher(content);

       while(matcher.find()){
        classNames.add(matcher.group(1));
       }
       return classNames;
    }

    private boolean isValidClassName(String className){
        //Checking if class begins with a capital letter
        if(!Character.isUpperCase(className.charAt(0))){
            System.out.println("Invalid class name: " + className + ". Class names should start with an uppeercase letter.");
        }

        //Checking for CamelCase
        for(int i = 1; i < className.length(); i++){
            char currentChar = className.charAt(i);

            if(!Character.isLetter(currentChar)){
                System.out.println("Invalid class name: " + className + ". Class names should only contain letters.");
                return false;
            }

            if(Character.isUpperCase(currentChar) && i > 1 && !Character.isUpperCase(className.charAt(i-1))){
                System.out.println("Invalid class name: " + className + ". Class names should follow CamelCase");
                return false;
            }
        }
        //once everything passed
        return true; 
    }

    
}
