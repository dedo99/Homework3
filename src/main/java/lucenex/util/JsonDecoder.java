package lucenex.util;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.apache.lucene.queryparser.flexible.messages.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonDecoder {

//    public static void main(String[] args) {
//
//        JSONParser parser = new JSONParser();
//        String s = "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
//
//        try{
//            Object obj = parser.parse(s);
//            JSONArray array = (JSONArray)obj;
//
//            System.out.println("The 2nd element of array");
//            System.out.println(array.get(1));
//            System.out.println();
//
//            JSONObject obj2 = (JSONObject)array.get(1);
//            System.out.println("Field \"1\"");
//            System.out.println(obj2.get("1"));
//
//            s = "{}";
//            obj = parser.parse(s);
//            System.out.println(obj);
//
//            s = "[5,]";
//            obj = parser.parse(s);
//            System.out.println(obj);
//
//            s = "[5,,2]";
//            obj = parser.parse(s);
//            System.out.println(obj);
//        }catch(ParseException pe) {
//
//            System.out.println("position: " + pe.getPosition());
//            System.out.println(pe);
//        }
//    }

    public List<Object> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMessagesArray(reader);
        } finally {
            reader.close();
        }
    }

    public List<Object> readMessagesArray(JsonReader reader) throws IOException {
        List<Object> messages = new ArrayList<Object>();

        reader.beginArray();
        while (reader.hasNext()) {
            readMessage(reader);
        }
        reader.endArray();
        return messages;
    }

    public void readMessage(JsonReader reader) throws IOException {
        long id = -1;
        String text = null;
//        User user = null;
        List<Double> geo = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextLong();
            } else if (name.equals("text")) {
                text = reader.nextString();
            } else if (name.equals("geo") && reader.peek() != JsonToken.NULL) {
                geo = readDoublesArray(reader);
            } else if (name.equals("user")) {
//                user = readUser(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    public List<Double> readDoublesArray(JsonReader reader) throws IOException {
        List<Double> doubles = new ArrayList<Double>();

        reader.beginArray();
        while (reader.hasNext()) {
            doubles.add(reader.nextDouble());
        }
        reader.endArray();
        return doubles;
    }

//    public User readUser(JsonReader reader) throws IOException {
//        String username = null;
//        int followersCount = -1;
//
//        reader.beginObject();
//        while (reader.hasNext()) {
//            String name = reader.nextName();
//            if (name.equals("name")) {
//                username = reader.nextString();
//            } else if (name.equals("followers_count")) {
//                followersCount = reader.nextInt();
//            } else {
//                reader.skipValue();
//            }
//        }
//        reader.endObject();
//        return new User(username, followersCount);
//    }

    public JSONArray parseJSONFile(String jsonFilePath) throws FileNotFoundException {
        //Get the JSON file, in this case is in ~/resources/test.json
        File file = new File(jsonFilePath);
        InputStream jsonFile = new FileInputStream(file);
        Reader readerJson = new InputStreamReader(jsonFile);

        //Parse the json file using simple-json library
        Object fileObjects = JSONValue.parse(readerJson);
        JSONArray arrayObjects=(JSONArray)fileObjects;

        return arrayObjects;
    }
}
