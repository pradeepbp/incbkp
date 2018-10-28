/**

    This class deals with directory operations like walkthrough, validations etc.

 */

import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;
import java.time.*;

 public class DirectOp{

    String fileSeparator;
    Path bkpLocation;
    String bkpName;
    
    // Constructor
    public DirectOp(Path bkpLocation){
        this.bkpLocation = bkpLocation;
        ConfigOp newConfOp = new ConfigOp(this.bkpLocation.toString());
        this.fileSeparator = newConfOp.getProperty("FileSeparator");
        String[] tempString = this.bkpLocation.toString().split(this.fileSeparator);
        this.bkpName = tempString[tempString.length - 1];
    }
  
    
    /**
        This function adds files in a folder to the backup database. Returns the number of files added.

    */

    public int addFolderToBkp(String folderPath){
        BkpFileVisitor fileVisitor = new BkpFileVisitor();
        ArrayList<String> list = new ArrayList<String>();

        Path fileLocPath = Paths.get(folderPath);
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
            
            // following for debugging only
            /*
            splitPath = file.split(this.fileSeparator);
            fileName =  splitPath[splitPath.length - 1];
            parentFolder = splitPath[0];
            for(int i = 1; i <= splitPath.length - 2; i ++){
                parentFolder = parentFolder + this.fileSeparator + splitPath[i];
            }
            
            System.out.println(parentFolder + "::" + fileName);
            */
            //System.out.println(fileName);
            addFileToBkp(file);
        }
           
        return list.size();
    }


    /**
        Adds a file to existing backup location; updates the data base with relevant file info
        
     */
    
    public void addFileToBkp(String filePathString){
        Path sourcePath = Paths.get(filePathString);

        //Get filename
        String[] filePathSplit = filePathString.split(this.fileSeparator);
        String origFileName = filePathSplit[filePathSplit.length - 1];

        
        String sourceFileLocation = filePathSplit[0];
        for(int i = 1; i <= filePathSplit.length - 2; i++){
            sourceFileLocation = sourceFileLocation + 
                                 this.fileSeparator + filePathSplit[i];
        }
        //System.out.println(sourceFileLocation); // for debugging only
        

        // Get timestamp and prepend to the original file name to avoid duplicates
        Instant timeStamp = Instant.now();
        String fileNameMod = new Long(timeStamp.toEpochMilli()).toString();
        String targetFileName = fileNameMod +"-"+ origFileName;

        // This assumes that 'bkp' subfolder is already available
        String targetPathString = this.bkpLocation.toString() + this.fileSeparator +
                                    "bkp" + this.fileSeparator + targetFileName;
        Path targetPath = Paths.get(targetPathString);

        //Copy file to target location
        try{
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch(IOException ioe){
            System.err.println("ERROR: Could not copy file to backup location");
        }

        CsvOp csvHandler = new CsvOp(this.bkpLocation + this.fileSeparator +
                                            this.bkpName);
        String dataRow  = targetFileName + "," + origFileName +","+ sourceFileLocation; 
        csvHandler.addRow(dataRow);
    }


} // end of DirectOp class


