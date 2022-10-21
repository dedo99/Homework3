package lucenex;

import lucenex.util.JsonDecoder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        String jsonFilePath = "C:\\Users\\andre\\Desktop\\tables\\tables.json";
        JsonDecoder jsondecoder = new JsonDecoder();
        try {
            JSONArray arrayObjects = jsondecoder.parseJSONFile(jsonFilePath);
            System.out.println();
        }catch (Exception e ){
            System.out.println("Eccezione");
        }

    }




}
