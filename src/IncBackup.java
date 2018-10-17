
/**

    IncBackup.
    Application entry point. User initiates the application by invoking the class
    generated from this source.
    Copyright (C) 2018  Pradeep Balan Pillai

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.


 */


import java.nio.file.*;
import java.util.*;
import java.io.*;

 class IncBackup{

     

     public static void main(String[] args){
        
        Path backupLocPath;
       /* 
        System.out.println("App Initialised...");
        //ConfigOp newOp = new ConfigOp();
		String input = null;
		if(args.length > 0){
			input = args[0];
		}
		System.out.println("Path is " + input);
        
		//Try creating a new backup location
        ConfigOp newOp = null;
        if(input != null){
			newOp = new ConfigOp(input);
		}
		else
			System.out.println("No path specified. Try again with a valid backup location");


        System.out.println(newOp.getBackupLocation());
        newOp.createCsvDataBase("csvdata");
        */

        AppIntroCmd();
     }// main function

    
    public static int AppIntroCmd(){
        Path listPath = Paths.get("bkplist");
        List<String> list =  null;
        
        System.out.println("Availabe backups are:");
        int i = 1;
        try{
            list  = Files.readAllLines(listPath);
            
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
                System.out.print("Select backup location:");
                Scanner scan = new Scanner(System.in);
                selection = scan.nextInt();
                break;
            }
            catch(InputMismatchException ime){
                System.out.println("Bad input; try again.");
            }
            
            
        }
        
        System.out.println("Selected " + list.get(selection - 1));
        return 0;
    } // end of function

 } // main class ends

 


