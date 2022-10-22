package lucenex;

import lucenex.util.JsonDecoder;
import org.apache.lucene.queryparser.flexible.messages.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String jsonFilePath = "C:\\Users\\andre\\Desktop\\tables\\tables.json";
        JsonDecoder jsondecoder = new JsonDecoder();
        try {
            InputStream inputStream = Files.newInputStream(Path.of(jsonFilePath));
            List<Object> arrayObjects = jsondecoder.readJsonStream(inputStream);
            System.out.println();
        }catch (Exception e ){
            System.out.println("Eccezione");
        }

    }




}
