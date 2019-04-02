import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;


//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;
public class Main{

    public static void main(String []args){
        if (args.length > 0) {
            try {
              BufferedWriter bw = new BufferedWriter(new FileWriter("index.html"));
              TextAnalyzer ta = null;
              long start = System.currentTimeMillis();
              if (args.length > 1) {
                ta = new TextAnalyzer(args[0], args[1]);
              } else {
                ta = new TextAnalyzer(args[0]);
              }
              TextAnalyzer.WordCounter[] arr = ta.topWords(30);
              long end = System.currentTimeMillis();
              System.err.println("Milliseconds:" + Long.toString(end - start));
              for (TextAnalyzer.WordCounter wc: arr) {
                System.out.println(wc.getWord() + ":\t" + wc.getCount());
              } 
              bw.write(ta.HTMLCloud(arr));
              bw.close();
            } catch (Exception e) {
              System.err.println(e);
            }
          }
    }
}