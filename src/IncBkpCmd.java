
import java.io.*;
import java.util.*;
import java.nio.file.*;

/**
        Command line operations of backup utility

 */


public class IncBkpCmd{

    /**
        Present an interactive session for user to select the backup location of her choice
     */

    public int AppIntroCmd(){
            Path listPath = Paths.get("bkplist");
            List<String> list =  null;
            
            System.out.println("Availabe backups are:");
            int i = 1;
            try{
                list  = Files.readAllLines(listPath);
                list.add("-> Exit");
                
                for(String item: list){
                    System.out.format("(%d) %s\n", i, item);
                    i = i + 1;
                }       
                
            }
            catch(IOException ioe){
                System.err.println("ERROR: Cannot open backup list");
            }
                        
            int selection = 0;
            
            while(true){
                try{
                    System.out.print("Select Option:");
                    Scanner scan = new Scanner(System.in);
                    selection = scan.nextInt();
                    checkRange(selection, list.size());
                    break;
                }
                catch(InputMismatchException ime){
                    System.out.println("Bad input; try again.");
                }          
                
            }
            
            System.out.println("Selected " + list.get(selection - 1));
            return selection;
        } // end of function

        /**
            This function checks whether the selected option is within the available optons or not
         */
        
        public void checkRange(int selection, int limit){
            if(selection > limit){
                throw new InputMismatchException();
            }
        }

        
        /**
            Interact with user; user selects the operation
        
         */
       

} // end of class