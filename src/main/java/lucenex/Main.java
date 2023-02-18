package lucenex;

import lucenex.index.JSONIndexer;
//import lucenex.model.JSONObject;
import lucenex.model.ItemResultQuery;
import lucenex.model.ListResultQuery;
import lucenex.model.ResultQuery;
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
            InputStream inputStream = Files.newInputStream(Paths.get("tables_final.json"));

            JSONIndexer.readJsonStream(inputStream, null);
//            Map<String, Integer> result = QueryManager.mergeList(4, new String[]{"Ajax", "Napoli", "Alcaraz", "Rangers"});
            ListResultQuery list_resultquery = new ListResultQuery();
            for (String key : maptoObj.keySet()){
                Map<String, Integer> result = QueryManager.mergeList(10, maptoObj.get(key).getQuery());
                ResultQuery resultQuery = new ResultQuery(key);
                System.out.println("-----------------RISULTATI " + key +" -----------------");
                for(String s : result.keySet()) {
                    System.out.println(s + " -> " + result.get(s));
                    List<String> split = List.of(s.split("_"));
                    ItemResultQuery item = new ItemResultQuery(split.get(0), split.get(1));
                    resultQuery.getList_item().add(item);
                }
                list_resultquery.getList_result().add(resultQuery);
                System.out.println("Top 3 previste " + key +" :" + maptoObj.get(key).getTop3());
            }
            write_to_json(list_resultquery);
        }catch (Exception e ){
            System.out.println("Eccezione");
            System.out.println(e.getMessage());
        }
    }

    public static void write_to_json(ListResultQuery list_resultquery){
        String path = "result_test.json";

        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(list_resultquery.toJson());
        } catch (Exception e) {
            e.printStackTrace();
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
            JSONArray a = (JSONArray) parser.parse(new FileReader("queries_final.json"));
            for (Object o : a)
            {
                TestObject testObject = new TestObject();

                JSONObject jsonObject = (JSONObject) o;

                Object name = jsonObject.get("name");
                List<String> lista_query = new ArrayList();
                // loop array
                JSONArray query = (JSONArray) jsonObject.get("query");
                for (Object c : query){
//                    System.out.println(c+"");
                    lista_query.add((String) c);
                }
                testObject.setQuery(lista_query);

                List<String> lista_top = new ArrayList();
                JSONArray top_3 = (JSONArray) jsonObject.get("top_3");
                for (Object c : top_3){
//                    System.out.println(c+"");
                    lista_top.add((String) c);
                }
                testObject.setTop3(lista_top);
                maptoObj.put((String) name, testObject);
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
