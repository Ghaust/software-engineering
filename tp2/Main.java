import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;
public class Main{

    public static void main(String []args){
        long start = System.currentTimeMillis();
        long end;
        long time;

        try{
        TextAnalyzer textAnalyzer = new TextAnalyzer("toto.txt");
        }catch(IOException e){
            
        }
        
        end = System.currentTimeMillis();
        time = end - start;
        System.out.println("Execution time : " + time+ "secondes");
    }
}