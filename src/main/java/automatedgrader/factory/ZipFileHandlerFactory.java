package automatedgrader.factory;

public class ZipFileHandlerFactory implements FileHandlerFactory{
    
    @Override
    public FileHandler createFileHandler(){
        return new ZipFileHandler();
    }
}
