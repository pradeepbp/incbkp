package csv;

import java.nio.file.*;
import java.io.*;
import java.util.*;


public class CsvOp{

    public Path csvPath = null;
    

    public CsvOp(String path){
        this.csvPath = Paths.get(path);

    }
    
    // Function to create a new csv file. Full path and column titles are
    // required to be passed
    public boolean createCsvFile(){
        boolean result = false;
        try{
            Files.createFile(csvPath);
            result = true;
        }
        catch(IOException ioe){
            System.err.println("ERROR: New CSV file creation failed");
        }
            return result;
    } // end of function
   


    /**
        Function to add a header row to csv file. A call to add header to file
        with data will cause it to be overwritten. Use with care!!
        
     */
    public boolean addHeader(String[] heads){
        boolean result = false;
        
            // Create a header string with ',' as delimiter
            String headerString = heads[0];
            for (int i = 1; i <= heads.length - 1; i++){
                headerString = headerString + "," + heads[i];
            }
            
            // Write header information to the csv file
            try(BufferedWriter writer = Files.newBufferedWriter(this.csvPath)){
                writer.write(headerString);
                writer.newLine();
                result = true;  
            }
            catch(IOException ioe){
                System.err.println("ERROR: Could not write to CSV file");
            }
                
        return result;
    } // end of function


    /**
        Function to return the size of data tokens in the header row, assuming\
        that the first row in the csv file is a header row.
     */

   // Function returns the number of items in the header
   public int getHeaderSize(){
       int size = 0;
       try(BufferedReader csvReader = Files.newBufferedReader(this.csvPath)){
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

    /**
        Function to add a new data row to existing csv file. The function
        does not check whether the data tokens match with header number or
        not.
     */

   public boolean addRow(String row){
       
       try(BufferedWriter writer = Files.newBufferedWriter(this.csvPath, StandardOpenOption.APPEND)){
           writer.write(row);
           writer.newLine();
       }
       catch(IOException ioe){
           System.err.println("ERROR: File error; cannot add data");
       }          
       return false;
   } // end of function

    
    
    /**
        Function to read a row of data from csv file. Returns the row as a string.
        [Current implementation reads all row before the target row is reached. This
        is inherently inefficient and hence needs to be changed]
     */

    public String getRow(int rowNumber){
        
        String rowData="";

       try(BufferedReader reader = Files.newBufferedReader(this.csvPath)){
           int line = 1;
           while(line < rowNumber){
               reader.readLine();
               line ++;
           }
           rowData = reader.readLine();

        }
       catch(IOException ioe){
           System.err.println("ERROR: Cannot read csv file");
       }
       
       return rowData;
    } // end of class


    /**
        Return a string containing the header tokens with comma
        as delimiter
     */
    public String getHeader(){
        return getRow(1);

    }


} // end of class