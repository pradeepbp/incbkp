package bkp;

import java.nio.file.*;
import java.io.*;


public class ConfigOp{

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


} // end of class