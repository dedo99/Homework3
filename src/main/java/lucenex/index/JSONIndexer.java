package lucenex.index;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lucenex.model.Cell;
import lucenex.model.JSONObject;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.simpletext.SimpleTextCodec;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class JSONIndexer {
    private static final Logger logger = Logger.getLogger(JSONIndexer.class.toString());
    private static final String INDEX_DIRECTORY = "index";


    public static void readJsonStream(InputStream in) throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            indexJSONStream(reader);
        }
    }

    public static void indexJSONStream(JsonReader reader) throws IOException {
        Directory indexDir = FSDirectory.open(Paths.get(INDEX_DIRECTORY));
        Map<String, String> colonnaXvalori = new HashMap<>();
        Gson gson = new Gson();
        Analyzer analyzer = new StandardAnalyzer();

        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        // iwc.setCodec(new SimpleTextCodec());

        IndexWriter indexWriter = new IndexWriter(indexDir, iwc);
        indexWriter.deleteAll();

        JSONObject obj = gson.fromJson(reader, JSONObject.class);

        int i = 0;

        while(obj != null) {
            for(Cell c : obj.getCells())
                if (!c.getHeader()) {
                    if(colonnaXvalori.containsKey(obj.getId() + "_" + c.getCoordinates().getColumn().toString())) {
                        String value = colonnaXvalori.get(obj.getId() + "_" + c.getCoordinates().getColumn().toString());
                        colonnaXvalori.put(obj.getId() + "_" + c.getCoordinates().getColumn().toString(), value + "~" + c.getCleanedText());
                    } else
                        colonnaXvalori.put(obj.getId() + "_" + c.getCoordinates().getColumn().toString(), c.getCleanedText());
                }

            for(String k : colonnaXvalori.keySet()) {
                Document doc = new Document();
                doc.add(new StringField("id", k, Field.Store.YES));
                doc.add(new TextField("value", colonnaXvalori.get(k), Field.Store.YES));
                indexWriter.addDocument(doc);
            }

            i++;
            try {
                obj = gson.fromJson(reader, JSONObject.class);
            } catch (Exception e) {
                // end of file
                break;
            }
            if(i == 100)
                break;
        }

        indexWriter.commit();
        indexWriter.close();

    }



}