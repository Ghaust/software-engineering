import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

/**
 *  @author Joseph-Emmanuel Banzio
 *  
 */
public class TextAnalyzer{
    private static HashMap<String, Integer> sortedArray = new HashMap<String, Integer>();
    //private static boolean ASC = true; 
    private static boolean DESC = false;

    class WordCounter{
       private String word;
       private int count;

       public WordCounter(String name, int rep){
           this.word = name;
           this.count = rep;
       }
       public int getCount(){
           return this.count;
       }

       public String getWord(){
        return this.word;
        }

    }

    /***
     * We store all the words of the file (and count them) in the sortedArray and after that we'll use the sort method to sort the file
     * @param fileName name of the file
     * @throws IOException
     */
    public TextAnalyzer(String fileName) throws IOException{
        String line = "";
        String word = "";

        BufferedReader br = null;
        int j = 0;

         
            
           br = new BufferedReader(new FileReader(fileName));
             
                //on parcourt le fichier
                while((line = br.readLine())!= null){
                    //on parcourt le string
                    j=0;
                    word="";
                    while(j<line.length()){
                        //tant qu'on a pas de séparateur
                        while(j<line.length() && (Character.isLetter(line.charAt(j)) || line.charAt(j)=='\'') ){
                             if(Character.isDigit(line.charAt(j+1)) ){ //to check
                                word="";
                                j++;
                             }else
                                 word+=line.charAt(j);
                            j++;
                        }

                        if(word.length() > 0 ){
                            if(!sortedArray.containsKey(word)) //mettre le mot en miniscule
                                sortedArray.put(word.toLowerCase(), 1);
                            else
                                sortedArray.put(word.toLowerCase(), sortedArray.get(word)+1);
                            word="";
                        }
                        j++;
                    }   
                }
             
              br.close();
            
             sort(DESC);
        
    }
    

    /**
     * this method returns the n highest number of the sorted array
     * @param n
     * @return WordCounter[] array
     */

    public WordCounter[] topWords(int n){
           
       int count = 0;
       
       ArrayList<WordCounter> wc = new ArrayList<>();
       for(Map.Entry mapEntry : TextAnalyzer.sortedArray.entrySet()){
            if(count==n ){
                break;
            }
            //how 
            wc.add(new WordCounter( mapEntry.getKey().toString(), (Integer) mapEntry.getValue()));
                count++;
       }
        
       TextAnalyzer.WordCounter[] wcArray = wc.toArray(new TextAnalyzer.WordCounter[wc.size()]);

        return wcArray;
     }

    public HashMap<String, Integer> getSortedArray(){
        return sortedArray;
    }

    /***
     * this method sort the array according to the order (DESC or ASC)
     * @param order
     */
    public void sort(boolean order){
        List<Entry<String, Integer>> list = new LinkedList<>(sortedArray.entrySet());
        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        sortedArray = list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new)); 
    
        }
}



