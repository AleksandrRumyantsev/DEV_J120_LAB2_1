import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;


public class MainClass {


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
                            //System.out.println(ss);
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //Создаем объект и передаем в него данные из книги:

        FreqDictionary freqDictionary = new FreqDictionary(list);
        //Создаем отчет "report - by - alph .txt"
        freqDictionary.createReportByAlph("report - by - alph .txt");
        //Создаем отчет report-by-alph-rev.txt с реверсивной сортировкой
        freqDictionary.createReportByAlphRev("report-by-alph-rev.txt");
        //Создаем отчет report-by-freq.txt  отчёт со списком слов, отсортированных по убыванию частоты
        freqDictionary.createReportByFreq("report-by-freq.txt");

    }



}