package lucenex;

import lucenex.index.JSONIndexer;
//import lucenex.model.JSONObject;
import lucenex.query.QueryManager;
import org.apache.lucene.codecs.simpletext.SimpleTextCodec;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
//            List<String> lista_nomi = read_csv();
            List <String> lista_query = read_json_query();
//            InputStream inputStream = Files.newInputStream(Paths.get("sample.json"));
//            InputStream inputStream = Files.newInputStream(Paths.get("tables.json"));
            InputStream inputStream = Files.newInputStream(Paths.get("tables_test.json"));

            JSONIndexer.readJsonStream(inputStream, null);
//            Map<String, Integer> result = QueryManager.mergeList(4, new String[]{"Ajax", "Napoli", "Alcaraz", "Rangers"});
            Map<String, Integer> result = QueryManager.mergeList(10, lista_query);
            System.out.println("-----------------RISULTATI-----------------");
            for(String s : result.keySet()) {
                System.out.println(s + " -> " + result.get(s));
            }
        }catch (Exception e ){
            System.out.println("Eccezione");
            System.out.println(e.getMessage());
        }

    }

    public static List<String> read_csv(){
        List<String> lista_nomi = new ArrayList();
        String line = "";
        final String delimiter = ",";
        try
        {
            String filePath = "solocountry.csv";
            FileReader fileReader = new FileReader(filePath);

            BufferedReader reader = new BufferedReader(fileReader);
            while ((line = reader.readLine()) != null)   //loops through every line until null found
            {
                String[] token = line.split(delimiter);    // separate every token by comma
                System.out.println(token[0]);
                lista_nomi.add(token[0]);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return lista_nomi;
    }

    public static List<String> read_json_query() {

        JSONParser parser = new JSONParser();
        List<String> lista_query = new ArrayList();

        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader("queries_test.json"));
            for (Object o : a)
            {
                JSONObject jsonObject = (JSONObject) o;

                // loop array
                JSONArray query = (JSONArray) jsonObject.get("query");
                for (Object c : query){
                    System.out.println(c+"");
                    lista_query.add((String) c);
                }
                JSONArray top_5 = (JSONArray) jsonObject.get("top_5");
                for (Object c : top_5){
                    System.out.println(c+"");
                    lista_query.add((String) c);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lista_query;
    }




}
