package lucenex.index;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


import lucenex.model.Cell;
import lucenex.model.JSONObject;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.pattern.PatternTokenizerFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.Codec;
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


    public static void readJsonStream(InputStream in, Codec codec) throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            indexJSONStream(reader, codec);
        }
    }

    public static void indexJSONStream(JsonReader reader, Codec codec) throws IOException {
        Directory indexDir = FSDirectory.open(Paths.get(INDEX_DIRECTORY));
        Map<String, String> colonnaXvalori = new HashMap<>();
        Gson gson = new Gson();
        Analyzer analyzer = CustomAnalyzer.builder()
                .withTokenizer(PatternTokenizerFactory.NAME, "pattern", "~", "group", "-1")
                .build();

        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        if (codec != null) {
            iwc.setCodec(codec);
        }

        IndexWriter indexWriter = new IndexWriter(indexDir, iwc);
        indexWriter.deleteAll();

        //acquisizione istante di tempo iniziale
        long startTime = System.nanoTime();

        JSONObject obj = gson.fromJson(reader, JSONObject.class);

        int num_table_index = 0;
        int num_col_index = 0;

        while(obj != null) {
            colonnaXvalori.clear();
            num_table_index++;
            for(Cell c : obj.getCells())
                if (!c.getHeader()) {
                    if(colonnaXvalori.containsKey(obj.getId() + "_" + c.getCoordinates().getColumn().toString())) {
                        String value = colonnaXvalori.get(obj.getId() + "_" + c.getCoordinates().getColumn().toString());
                        colonnaXvalori.put(obj.getId() + "_" + c.getCoordinates().getColumn().toString(), value + "~" + c.getCleanedText());
                    } else
                        colonnaXvalori.put(obj.getId() + "_" + c.getCoordinates().getColumn().toString(), c.getCleanedText());
                }


            for(String k : colonnaXvalori.keySet()) {
                num_col_index++;
                Document doc = new Document();
                doc.add(new StringField("id", k, Field.Store.YES));
                doc.add(new TextField("value", colonnaXvalori.get(k), Field.Store.YES));
                System.out.println(num_col_index);
                try {
                    indexWriter.addDocument(doc);
                } catch (IOException e) {
                    logger.severe("Exception while inserting document");
                    logger.severe(e.getMessage());
                }
            }

            try {
                obj = gson.fromJson(reader, JSONObject.class);
            } catch (Exception e) {
                // end of file
                break;
            }
        }

        indexWriter.commit();
        indexWriter.close();

        //acquisizione istante di tempo finale
        long endTime = System.nanoTime();
        // ottiene la differenza tra i due valori di tempo
        long timeElapsed = endTime - startTime;
        //stampe
        System.out.println("Number of tables indexed: " + num_table_index);
        System.out.println("Number of documents indexed: " + num_col_index);
        int div_for_milli = 1000000;
        System.out.println("Execution time in milliseconds for index: " + timeElapsed / div_for_milli);

    }


}