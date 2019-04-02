import java.util.ArrayList;
import java.util.Arrays;

public class Main{

    public static void main(String[]args){
        ReadFile fRead = new ReadFile();
        System.out.println(fRead.load("world_cities.csv", ','));

        //ArrayList<ArrayList<String>> array = fRead.getData();

        System.out.println(fRead.fieldCount());
    }

}