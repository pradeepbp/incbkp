
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

    public Path getSelectionOfBkpLocationInCmd(){
            Path listPath = Paths.get("bkplist");
            List<String> list =  null;
            
            System.out.println("Choose a backup location:");
            int i = 1;
            try{
                list  = Files.readAllLines(listPath);
                list.add("Exit");
                
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
            return Paths.get(list.get(selection - 1));
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
       
        public int getUserIntroOption(){
            
            String[] options = {"Create a new backup",
                                "Add files/folders to an existing backup",
                                "Restore files from an existing backup",
                                "Exit"};
            
            // Print welcome message with version details
            System.out.println("\n\nIncremental Backup Utility, Version: Alpha\n");    


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

        public Path createBackupInCmd(){
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
            String bkpName = null;
            if(flag){
                System.out.print("Enter backup name without ant special charecters...\n" + 
                                  "(any existing folder with " +
                                  "same name will be overwritten):");
                Scanner input = new Scanner(System.in);
                bkpName = input.next();

                FileSystem currentFileSystem = FileSystems.getDefault();
                String separator = currentFileSystem.getSeparator();
                
                bkpLoc = location + separator + bkpName;
            }
            ConfigOp config = new ConfigOp(bkpLoc);
            
            config.createBkpLocation();
            config.createCsvDataBase(bkpName);
            return Paths.get(bkpLoc);
        } 


    /**
        Function to add files/folders to an existing backup from commmand line
        
     */

    public void addItemToBackupInCmd(){
        Path selectedBkp = getSelectionOfBkpLocationInCmd();
        System.out.print("Enter path of file to add:");
        Scanner scan = new Scanner(System.in);
        String filePathString = scan.nextLine(); // do not use can.next()
                                                 // as it will skip whitespaces

        /*
            This will check whether the chosen path represents a file or a folder
            and calls the appropriate response
        */
        DirectOp op = new DirectOp(selectedBkp);
        if(Files.isDirectory(selectedBkp)){
            System.out.println("Folder selected. Files in the folder" +
                                " will be backed up");
            op.addFolderToBkp(filePathString);
        }
        else{
            
            op.addFileToBkp(filePathString);
        }
     

        
    }


    /**
        App initiating function which interacts with user and responds based on the 
        user input.
     */

    public void interactWithUserInCmd(){
        int option = getUserIntroOption();

        switch(option){
            case 1: createBackupInCmd();
                    break;
            case 2: addItemToBackupInCmd();       
                    break;
        }
    }

} // end of class