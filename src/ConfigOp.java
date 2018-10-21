

import java.nio.file.*;
import java.io.*;
import java.util.*;


public class ConfigOp{

    private String configFileName = ".bkpconfig";
    Path bkpLocation = null;
    Path configFilePath = null;
    String separator = "";


    // The following String array contains the header tokens for csv database file
    private String[] header = {"modFileName", "OriginalFileName", "ParentDirectory"};


    public ConfigOp(String bkpPath){
        this.bkpLocation = Paths.get(bkpPath);
        
        FileSystem currentFileSystem = FileSystems.getDefault();
        this.separator = currentFileSystem.getSeparator();
                
        String filePathString = this.bkpLocation.toString() + this.separator + this.configFileName;
        this.configFilePath = Paths.get(filePathString);

    } // end of constructor



    /**
        Creates a folder at backup location chosen by the user
     */

    public boolean createBkpLocation(){
        
        try{
            Files.createDirectory(this.bkpLocation);
            createConfigFile();
            updateMasterListOfBkps(this.bkpLocation);
            return true;
        }
        catch(IOException ioe){
            System.err.println("ERROR:could not create backup location");
            return false;
        }
        
    }

    public void updateMasterListOfBkps(Path location){
        Path pathMaster = Paths.get("bkplist");
        try(BufferedWriter writer = Files.newBufferedWriter(pathMaster, StandardOpenOption.APPEND)){
            writer.write(location.toString());
            writer.newLine();
        }
        catch(IOException ioe){
            System.err.println("ERROR: Master file updation failed");
        }
    }


    /**
        Create a new config file and populate it with metadata
     */

    public Path createConfigFile(){
        int errorCode = 0;   
        try{
            Files.createFile(this.configFilePath); 
        }
        catch(IOException ioe){
            System.err.println("ERROR:Could not create config file");
            errorCode = 1;
        }

        // Add metadata
         String[] keys = {"BackUpPath",
                         "FileSeparator"
                        };

        String[] values = {this.bkpLocation.toString(),
                            this.separator
                            };
        addProperties(keys, values);

        return this.configFilePath;
    } // end of function

   


    /**
        Adds metadata to config file in the form key:value pairs
     */

    public boolean addProperties(String[] keys, String[] values){
        boolean result = false;
        if(keys.length == values.length){
            Properties bkpProperties = new Properties();
            
            int i;
            for(i = 0; i <= keys.length - 1; i ++){
                bkpProperties.setProperty(keys[i], values[i]);
            }

            try(BufferedWriter writer = Files.newBufferedWriter(this.configFilePath,
                                        StandardOpenOption.APPEND)){
            bkpProperties.store(writer,"");
            result = true;
            }
            catch(IOException ioe){
                System.err.println("ERROR: Cannot write properties to config file");
            }
        }
        return result;
    } // end of function




    /**
        Returns the 'value' corresponding to the 'key' from config properties
     */

    public String getProperty(String key){
        Properties bkpProperties = new Properties();
        String value = "";
        try(BufferedReader reader = Files.newBufferedReader(this.configFilePath)){
            bkpProperties.load(reader);
            value = bkpProperties.getProperty(key);
        }
        catch(IOException ioe){
            System.err.println("ERROR: Could not reader config property");
        }

        return value;
    }


    /**
        Returns the backup location in the form of a String
     */

    public String getBackupLocation(){
        return this.bkpLocation.toString();
    } 

    
    /**
        Creates a csv database file with headers
     */

    public void createCsvDataBase(String FileName){
        
        CsvOp csvop = new CsvOp(this.bkpLocation.toString() + 
                            this.separator.toString() + 
                            FileName);
        csvop.createCsvFile();
        csvop.addHeader(header);

    } // end of function

} // end of class
