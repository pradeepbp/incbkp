package csv;

import java.nio.file.*;
import java.io.*;
import java.util.*;


public class CsvOp{

    private Path csvPath = null;
    boolean result = false;

    public CsvOp(String path){
        this.csvPath = Paths.get(path);

    }
    
    // Function to create a new csv file. Full path and column titles are
    // required to be passed
    public boolean createCsvFile(String[] heads){
        //csvPath = filePath;
        try{
            Files.createFile(csvPath);
        }
        catch(IOException ioe){
            System.err.println("ERROR: New CSV file creation failed");
        }
                
        //Create header String
        String headerString = heads[0];
        for (int i = 1; i <= heads.length - 1; i++){
            headerString = headerString + "," + heads[i];
        }
        
        // Write header information to the csv file
        try(BufferedWriter csvWriter = Files.newBufferedWriter(csvPath)){
            csvWriter.write(headerString);
            csvWriter.newLine();
            result = true;  
        }
        catch(IOException ioe){
            System.err.println("ERROR: Could not write to CSV file");
        }

        return result;
    } // end of function
   



   // Function returns the number of items in the header
   public int getHeaderSize(){
       int size = 0;
       try(BufferedReader csvReader = Files.newBufferedReader(csvPath)){
           String headerLine = csvReader.readLine();
           String[] heads = headerLine.split(",");
           size = heads.length;
       }
       catch(IOException ioe){
           System.err.println("ERROR: Cannot read csv header");
       }
       finally{
           return size;
       }
 
   }



   // Function to append a row of data. Checks for length of header and items
   // in the input row before performing the operation 
   public boolean writeRow(){
       return false;
   }

    public void getHeader(){

    }

    public void getValue(int row, String head){

    }

} // end of class