package lucenex;

import lucenex.index.JSONIndexer;
//import lucenex.model.JSONObject;
import lucenex.model.TestObject;
import lucenex.query.QueryManager;
import org.apache.lucene.codecs.simpletext.SimpleTextCodec;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
//            List<String> lista_nomi = read_csv();
            Map<String, TestObject> maptoObj = read_json_query();
//            InputStream inputStream = Files.newInputStream(Paths.get("sample.json"));
//            InputStream inputStream = Files.newInputStream(Paths.get("tables.json"));
            InputStream inputStream = Files.newInputStream(Paths.get("tables_test_finale.json"));

            JSONIndexer.readJsonStream(inputStream, null);
//            Map<String, Integer> result = QueryManager.mergeList(4, new String[]{"Ajax", "Napoli", "Alcaraz", "Rangers"});
            for (String key : maptoObj.keySet()){
                Map<String, Integer> result = QueryManager.mergeList(5, maptoObj.get(key).getQuery());
                System.out.println("-----------------RISULTATI " + key +" -----------------");
                for(String s : result.keySet()) {
                    System.out.println(s + " -> " + result.get(s));
                }
                System.out.println("Top 5 previste " + key +" :" + maptoObj.get(key).getTop5());
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

    public static Map<String, TestObject> read_json_query() {

        JSONParser parser = new JSONParser();
        Map<String, TestObject> maptoObj = new HashMap<>();

        try {
            int i = 1;
            JSONArray a = (JSONArray) parser.parse(new FileReader("queries_test_finale_prova.json"));
            for (Object o : a)
            {
                TestObject testObject = new TestObject();

                JSONObject jsonObject = (JSONObject) o;

                List<String> lista_query = new ArrayList();
                // loop array
                JSONArray query = (JSONArray) jsonObject.get("query");
                for (Object c : query){
//                    System.out.println(c+"");
                    lista_query.add((String) c);
                }
                testObject.setQuery(lista_query);

                List<String> lista_top = new ArrayList();
                JSONArray top_5 = (JSONArray) jsonObject.get("top_5");
                for (Object c : top_5){
//                    System.out.println(c+"");
                    lista_top.add((String) c);
                }
                testObject.setTop5(lista_top);
                maptoObj.put("query"+i, testObject);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return maptoObj;
    }




}
