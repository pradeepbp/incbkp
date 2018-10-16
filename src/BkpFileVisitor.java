import java.nio.file.attribute.*;
import java.nio.file.*;
import java.util.*;


public class BkpFileVisitor extends SimpleFileVisitor<Path>{

    private static ArrayList<String> fileList = new ArrayList<String>();
       
    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attr){
        String stringPath = path.toString();
        fileList.add(stringPath);
        //String[] splitPath = stringPath.split("/");
        //System.out.println(stringPath + "-" + splitPath[splitPath.length - 1]);

        return FileVisitResult.CONTINUE;
    }


    public ArrayList<String> getFileList(){
        return this.fileList;
    }
    
} // end of visitor class