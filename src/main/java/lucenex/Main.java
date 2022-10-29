package lucenex;

import lucenex.index.JSONIndexer;
import lucenex.query.QueryManager;
import org.apache.lucene.codecs.simpletext.SimpleTextCodec;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
//            InputStream inputStream = Files.newInputStream(Paths.get("sample.json"));
            InputStream inputStream = Files.newInputStream(Paths.get("tables.json"));
            JSONIndexer.readJsonStream(inputStream, new SimpleTextCodec());
//            Map<String, Integer> result = QueryManager.mergeList(4, new String[]{"Ajax", "Napoli", "Alcaraz", "Rangers"});
            Map<String, Integer> result = QueryManager.mergeList(4, new String[]{"ina shan", "1963"});
            System.out.println("-----------------RISULTATI-----------------");
            for(String s : result.keySet()) {
                System.out.println(s + " -> " + result.get(s));
            }
        }catch (Exception e ){
            System.out.println("Eccezione");
            System.out.println(e.getMessage());
        }

    }




}
