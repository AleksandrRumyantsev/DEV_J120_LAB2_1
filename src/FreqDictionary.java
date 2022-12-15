import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class FreqDictionary {
    public void createFiles (String name, Collection <String> collection){
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
                for (String s:collection){
                    writer.write(s+"\n");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main(String[] args) {
        //String filename = args[0]; //получаем ние файла из аргумента командной строки
        String filename = "j120-lab2.txt";
        ArrayList <String> list = new ArrayList<>();

        //Если аргумент пустой, запрашиваем ввод имени файла в консоли
        if (filename.isEmpty()) {
            System.out.println("Файла не существует! Введите название файла!:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                filename  = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //Получаем весь список слов из текста
        File file = new File(filename);
        if (file.canRead()){
            try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(filename), Charset.forName("UTF-8"))) {
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    //System.out.println(line);
                    String [] arrStr = line.split("\\p{P}*[ \\t\\n\\r]+\\p{P}*|^\\p{P}|\\p{P}$|[ \\t\\n\\r\\p{P}]{2,}"); //Делим строки на слова, игнорируя пунктуацию
                    for (String ss:arrStr){
                        if (!ss.isEmpty()) {
                            list.add(ss.toLowerCase()); //Добавляем слова в список в нижнем регистре
                            System.out.println(ss);
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //Делаем отчет со списком слов отсортированным по алфавиту:
        Collections.sort(list);
        //Создаем отчет "report - by - alph .txt"
        FreqDictionary freqDictionary = new FreqDictionary();
        freqDictionary.createFiles("report - by - alph .txt", list);
        //Создаем отчет report-by-alph-rev.txt с реверсивной сортировкой
        Collections.reverse(list);
        freqDictionary.createFiles("report-by-alph-rev.txt", list);
    }



}