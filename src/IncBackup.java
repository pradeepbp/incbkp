
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

import bkp.*;
import java.nio.file.*;

 class IncBackup{
     public static void main(String[] args){
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
            //boolean result = newOp.createBkpLocation();
			//System.out.println(result);
		}
		else
			System.out.println("No path specified. Try again with a valid backup location");

        //newOp.createConfigFile();
        //System.out.println(newOp.getProperty("FileSeparator"));
        System.out.println(newOp.getBackupLocation());
        newOp.createCsvDataBase("csvdata");
         
     }// main function

       

 } // main class ends


