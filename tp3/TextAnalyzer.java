import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.paint.Color;

import java.util.Collections;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 *   Extraction of words from a text.
 *   <p>
 *   This class loads words from text and counts occurrences.
 *   It provides methods for retrieving the most frequent words.
 */
public class TextAnalyzer {

    private HashMap<String,Integer> _hm = null;
    private ArrayList<WordCounter>  _al = null;
    private ArrayList<String> _sw = null; //arraylist of stop words

    /**
     *     Utility class.
     *     <p>
     *     It packs together one word and the number of times
     *     it appears.
     */
    public class WordCounter
            implements Comparable<WordCounter> {
        private String _w;
        private int    _cnt;

        /**
         *    Plain constructor.
         *
         *    @param w A word.
         *    @param cnt The number of times it appears in the text.
         */
        public WordCounter(String w, int cnt) {
            _w = w;
            _cnt = cnt;
        }

        /**
         *    Getter.
         *
         *    @return A word.
         */
        public String getWord() {
          return _w;
        }

        /**
         *    Getter.
         *
         *    @return A number of occurrences.
         */
        public int getCount() {
          return _cnt;
        }

        /**
         *    Comparator of two WordCounter objects.
         *    <p>
         *    Note that as we are interested in most
         *    frequent objects, the default sort is
         *    from most frequent to least frequent.
         *
         *    @param  wc A WordCounter object to compare to the current one.
         *    @return -1 if the current object appears more than the one that
         *            is compared, 1 if it appears less, and if they appear
         *            the same number of times we fall back to String
         *            comparison and sort by (regular) alphabetical order.
         */
        public int compareTo(WordCounter wc) {
           if (wc == null) {
             return 1;
           }
           if (this._cnt > wc._cnt) {
             return -1;
           } else if (this._cnt < wc._cnt) {
             return 1;
           } else {
             return this._w.compareTo(wc._w);
           }
        }
    }

    /**
     *    Constructor
     *
     *    @param fname Name of a file that contains the text to analyze.
     *    @throws IOException if anything goes wrong with the file.
     */
    public TextAnalyzer(String fname) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fname));
        StringBuffer   sb = new StringBuffer();
        int            i;
        int            j;
        char           c;
        Integer        occ;
        String         w;
        _hm = new HashMap<String,Integer>();
        _al = new ArrayList<WordCounter>();
        while ((i = br.read()) != -1) {
          c = (char)i;
          if (Character.isLetter(c) || (c == '\'')) {
            sb.append(c);
          } else if (Character.isDigit(c)) {
            // Forget
            sb.setLength(0);
          } else { // End of word
            if (sb.length() > 0) {
              // Suppress single quotes at the beginning or end
              while ((sb.length() > 0)
                     && (sb.charAt(0) == '\'')) {
                sb = sb.deleteCharAt(0);
              }
              j = sb.length() - 1;
              while ((j >= 0) && (sb.charAt(j) == '\'')) {
                sb = sb.deleteCharAt(j);
                j--;
              }
              if (sb.length() > 0) {
                w = sb.toString().toLowerCase();
                occ = _hm.get(w);
                if (occ == null) {
                  _hm.put(w, 1);
                } else {
                  _hm.put(w, occ + 1);
                } 
                sb.setLength(0);
              }
            }
          }
        }
        br.close();
        for (Map.Entry<String,Integer> me: _hm.entrySet()) {
          _al.add(new WordCounter(me.getKey(), me.getValue()));
        }
        Collections.sort(_al);
        System.out.println(_al.size());
    }

     /**
     *    Constructor
     *    The constructor calls the first one in order to analyze and store the first file
     *    @param fname Name of a file that contains the text to analyze.
     *    @param stopWords Name of a file that contains a stop words' list
     *    @throws IOException if anything goes wrong with the file
     */
    public TextAnalyzer(String fname, String stopWords) throws IOException{

          this(fname);
          BufferedReader br = new BufferedReader(new FileReader(stopWords));
          String line = "";
          
          this._sw = new ArrayList<String>();
          int i;
          boolean exists = false;
          Iterator<WordCounter> it = _al.iterator();
          while((line = br.readLine()) != null){
             this._sw.add(line);
          }
          br.close();


          while(it.hasNext()){
              String word = it.next().getWord();
              i=0;
              while(i< _sw.size() ){
                  if(_sw.get(i).equals(word)){
                    exists = true;
                    break;
                  }
                  else
                    exists = false;
                  i++;
              }
              if(!exists)
                it.remove();
          }

    }
    
    /***
     * Return a HTML page as a String
     * The returned string can be stored in a html file and be open on a navigator
     * The color of each word is chosen randomly
     * The font size decreases progressively with a rand value between 50 to 100
     * If two words have the same number of occurences, they will have the same size but not the same color.
     *  @param words Array of WordCounter that has been sorted from most frequent to least frequent
     */
    public String HTMLCloud(WordCounter[] words){
      String hPBeginning = "<html> <body> <div>";
      String hPEnding = "</div> </body> </html>";
      float percentage = 2000;
      String[] colors = {"aqua", "black", "blue", "fuchsia", "gray", "green", "lime", "maroon", "navy", "olive", "orange", "purple", "red", "silver", "teal", "yellow"};

      for(int i=0; i<words.length; i++){
        
        hPBeginning += " <span style=\"color:" + colors[new Random().nextInt(colors.length)]
        + "; font-size:" + percentage + "%\">" + words[i].getWord() + "</span>";
        //we decreased the percentage since WordCounter is sorted from most frequent to least frequent
        if(!Arrays.asList(words).contains( (Object) words[i].getCount()))
            percentage -= ThreadLocalRandom.current().nextInt(50, 100);
        
      }
      

      return hPBeginning + hPEnding;
    }
    
    /**
     *     Returns the most frequent words.
     *     <p>
     *     Words are returned in lower case. Note that if some words
     *     appear the same number of times they are all returned. It is
     *     thus possible to get back an array that contains more values
     *     than required.
     *
     *     @param n The number of words to return.
     *     @return An array of WordCounter objects, sorted from most frequent
     *             to least frequent.
     */
    public WordCounter[] topWords(int n) {
        if (n <= 0) {
          return null;
        }
        int i = 0;
        while ((i <= n)
              || _al.get(i).getCount() == _al.get(i-1).getCount()) {
          i++;
        }
        WordCounter[] arr = new WordCounter[i];
        return _al.subList(0, i).toArray(arr);
    }

    public ArrayList<WordCounter> get_al(){
        return this._al;
    }
}