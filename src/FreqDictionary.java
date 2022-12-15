import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FreqDictionary {
    private  int count =0;
    private ArrayList<String> collection;

    public FreqDictionary(ArrayList<String> collection) {
        Collections.sort(collection);
        this.collection = collection;
        this.count = collection.size();
    }


    private LinkedHashMap <String, Integer> createSortedMapByKey (ArrayList<String> list){
        LinkedHashMap<String, Integer> sortedList = new LinkedHashMap<>();
        for (String s:list){
            if (sortedList.containsKey(s)){
                sortedList.put(s,sortedList.get(s)+1);
            }else{
                sortedList.put(s,1);
            }
        }
        return sortedList;
    }
    private void createFiles (String name, Map<String,Integer > sortedList){

        File file = new File(name);
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        if (file.canWrite()){
            try (FileWriter writer = new FileWriter(file, false)){
                writer.write("Всего слов в книге: " + count + "\n");
                sortedList.forEach((key, value)->{
                    try {
                        double d = (double) value/count;
                        System.out.println(value+ " / " + count + " = " + d);
                        writer.write("Слово: " + key + "     Абсол. част.: " + value + "    раз. Относ. част.:   " + d + "\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void createReportByAlph (String name){
        this.createFiles(name,this.createSortedMapByKey(collection));
    }
    public void createReportByAlphRev (String name){
        ArrayList<String> col2 = new ArrayList<> (collection);
        Collections.reverse(col2);
        this.createFiles(name,this.createSortedMapByKey(col2));
    }
    public void createReportByFreq (String name){
        TreeMap <String, Integer> treeMap = new TreeMap<>(createSortedMapByKey(collection));
        this.createFiles(name,treeMap);
    }
}
