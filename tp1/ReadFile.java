
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ReadFile{
    private ArrayList<ArrayList<String>> file = new ArrayList<>();


    public int load(String filename, char sep){
        BufferedReader reader = null;
        FileReader fRead =  null;
        String line = "";
        String[] tab = null;
      

        int nbL = 0;
        
        try{
            fRead = new FileReader(filename);
            reader = new BufferedReader(fRead);
            try{
                    line = reader.readLine();
                    while(line != null){
                        nbL++;
                        ArrayList<String>arrayList = new ArrayList<>();
                        tab = line.split(sep+"");
                        
                        
                        for(int i=0; i<tab.length; i++){
                            arrayList.add(tab[i]);
                        }

                        this.file.add(arrayList);

                        try{
                            line = reader.readLine();
                        }catch(IOException e){
                            System.out.println(e.getMessage());
                        }
                    }

                    try{
                        reader.close(); 
                    }catch(IOException e){
                        System.out.println(e.getMessage());
                    }
                }catch(IOException e){
                    System.out.println(e.getMessage());
                }

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return nbL;
    }

    public int fieldCount(){
        int fieldNb = 0;
        int lastNb = 0;
        for(ArrayList<String> tab : this.file){
            
            for(int i=0; i<tab.size(); i++){
                fieldNb++;
            }
            
            if(fieldNb > lastNb)
                lastNb = fieldNb;
            fieldNb = 0;
        }

        return lastNb;
    }

    public ArrayList<ArrayList<String>> getData(){
        return this.file;
    }

}