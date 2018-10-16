/**

    This class deals with directory operations like walkthrough, validations etc.

 */

import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;

 public class DirectOp{

    String fileSeparator;
    Path bkpLocation;
    
    // Constructor
    public DirectOp(Path bkpLocation){
        this.bkpLocation = bkpLocation;
        ConfigOp newConfOp = new ConfigOp(this.bkpLocation.toString());
        this.fileSeparator = newConfOp.getProperty("FileSeparator");
    }
  
    
    /**
        This function adds files in a folder to the backup database. Returns the number of files added.

    */

    public int addFilesToBase(String fileLocation){
        BkpFileVisitor fileVisitor = new BkpFileVisitor();
        ArrayList<String> list = new ArrayList<String>();

        Path fileLocPath = Paths.get(fileLocation);
        System.out.println(fileLocPath.toString());
        try{
            Path p = Files.walkFileTree(fileLocPath, fileVisitor);
        }
        catch(IOException ioe){
            System.out.println("ERROR: Failed to read location!");
        }

        list = fileVisitor.getFileList();
        
        String fileName, parentFolder;
        String[] splitPath;
        for(String file: list){
            splitPath = file.split(this.fileSeparator);
            fileName =  splitPath[splitPath.length - 1];
            parentFolder = "";
            for(int i = 0; i <= splitPath.length - 2; i ++){
                parentFolder = parentFolder + splitPath[i] + this.fileSeparator;
            }
            System.out.println(parentFolder + "::" + fileName);
            //System.out.println(fileName);
        }
        
        return list.size();
    }

} // end of DirectOp class


