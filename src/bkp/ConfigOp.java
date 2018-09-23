package bkp;

import java.nio.file.*;
import java.io.*;


public class ConfigOp{

    private String configFileName = ".bkpconfig";

    // Create a path object pointing to the backup destination
    Path bkpPath;
    public Path createPath(String bkpLocation){
       try{
            return Paths.get(bkpLocation);
        }
        catch(InvalidPathException ipe){
            System.out.println("ERROR: cannot create path object");
            return null;
        }
              
    }

    // Create a folder at backup location. Returns 'true' if it succeeds, else returns 'false'.
    public boolean createBkpLocation(Path location){
        
        Path pathLocation;
        try{
            pathLocation = Files.createDirectory(location);
            return true;
        }
        catch(IOException ioe){
            System.err.println("ERROR:could not create backup location");
            return false;
        }
        
    }

    //Function to create a new config file at backup location and populate with
    //metadata

    public Path createConfigFile(Path configFileLocation){
        
        FileSystem currentFileSystem = FileSystems.getDefault();
        String separator = currentFileSystem.getSeparator();
        System.out.println("File separator is "+separator);
        System.out.println("File system is +"+currentFileSystem.toString()+
                            "provided by "+currentFileSystem.provider().toString());
        
        String filePathString = configFileLocation.toString() + separator + configFileName;
        System.out.println(filePathString);
        Path configFilePath = Paths.get(filePathString);
        Path returnPath = null;
        try{
            returnPath = Files.createFile(configFilePath); 
        }
        catch(IOException ioe){
            System.err.println("ERROR:Could not create config file");
        }
        
        return returnPath;
    }

} // end of class