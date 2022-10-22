package lucenex.util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import lucenex.model.Cell;
import lucenex.model.JSONObject;
import org.apache.lucene.queryparser.flexible.messages.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonDecoder {
    public void readJsonStream(InputStream in) throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            readMessagesArray(reader);
        }
    }

    public JSONObject readMessagesArray(JsonReader reader) throws IOException {

        Gson gson = new Gson();

        long i = 0;

        List<JSONObject> list = new ArrayList<>();

        JSONObject obj = gson.fromJson(reader, JSONObject.class);

        while(obj != null) {
            i++;
            obj = gson.fromJson(reader, JSONObject.class);
        }

        System.out.println(i);



        return obj;
    }


}
