

/**
    Testing class

*/
import java.nio.file.*;

public class Test{

    public static void main(String[] args){

        Path path = Paths.get("/home/pradeep/temp/today");
        DirectOp dop = new DirectOp(path);
        System.out.println(dop.addFilesToBase("/home/pradeep/temp"));

    }

}