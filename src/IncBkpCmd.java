
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
            Interact with user; get the operation selected by user
        
         */
       
        public int getUserOption(){
            
            String[] options = {"Create a new backup",
                                "Open an existing backup",
                                "Restore files from an existing backup",
                                "Exit"};
            

            // Add options to a list
            ArrayList<String> list = new ArrayList<String>();
            for(String item : options){
                list.add(item);
            }

            // Print options to screen
            int i = 1;
            for(String item : list){
                System.out.format("(%d) %s\n", i, item);
                i ++;
            }

            // Get correct input from user
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
            System.out.println(selection);
            return selection;
        }

        /**
            Reads the location where user wants to create a new backup. Returns path to
            the location

         */

        public Path getNewBackupLocation(){
            Path bkpPath = null;
            boolean flag = false;
            String location = null;
            String  bkpLoc = null;
            while(flag == false){
                System.out.print("Enter location where you want to create backup:");
                Scanner scan = new Scanner(System.in);
                location = scan.next();

                //validate location
                try{
                    flag = Files.isDirectory(Paths.get(location));
                    
                    if(flag == false){
                        System.out.println("Invalid location; try again...");
                    }
                }
                catch(SecurityException se){
                    System.err.println("ERROR: Cannot access backup location");
                }
            }
            // Get backup name
            
            if(flag){
                System.out.print("Enter backup name without ant special charecters...\n" + 
                                  "(any existing folder with " +
                                  "same name will be overwritten):");
                Scanner input = new Scanner(System.in);
                String bkpName = input.next();

                FileSystem currentFileSystem = FileSystems.getDefault();
                String separator = currentFileSystem.getSeparator();
                
                bkpLoc = location + separator + bkpName;
            }
            return Paths.get(bkpLoc);
        } 

} // end of class