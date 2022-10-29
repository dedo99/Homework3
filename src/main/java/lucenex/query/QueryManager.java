package lucenex.query;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

public class QueryManager {
    private static final Logger logger = Logger.getLogger(QueryManager.class.toString());
    private static final String INDEX_DIRECTORY = "index";

    private Map<String, Integer> set2count;

    public static Map<String, Integer> mergeList(int n, String[] queryString) {
        long startTime = System.nanoTime();
        Map<String, Integer> queryResult = executeQuery("value", queryString);
        Map<String, Integer> result = new HashMap<>();
        int i = 0;
        for(String s : queryResult.keySet()) {
            if(i>=n) break;
            result.put(s, queryResult.get(s));
            i++;
        }

        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        int div_for_milli = 1000000;
        System.out.println("Execution time in milliseconds for query: " + timeElapsed / div_for_milli);

        return sortMapByValues(result);
    }

    public static Map<String, Integer> executeQuery(String field, String[] queryString) {
        Map<String, Integer> columnsXcount = new HashMap<>();
        try {
            for(String s : queryString) {
                TermQuery termQuery = new TermQuery(new Term(field, s));

                try (Directory directory = FSDirectory.open(Paths.get(INDEX_DIRECTORY))) {
                    try (IndexReader reader = DirectoryReader.open(directory)) {
                        IndexSearcher searcher = new IndexSearcher(reader);
                        runQuery(searcher, termQuery, columnsXcount);
                    } finally {
                        directory.close();
                    }
                }
            }
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }

        return sortMapByValues(columnsXcount);
    }

    private static HashMap<String, Integer> sortMapByValues(Map<String, Integer> columnsXcount) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(columnsXcount.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }

        return temp;
    }

    private static void runQuery(IndexSearcher searcher, Query query,  Map<String, Integer> columnsXcount) throws IOException {
        int totalHits = searcher.count(query);;
        Set<String> columns = new HashSet<>();
        if(totalHits > 0) {
            TopDocs hits = searcher.search(query, totalHits);
            for (int i = 0; i < hits.scoreDocs.length; i++) {
                ScoreDoc scoreDoc = hits.scoreDocs[i];
                Document doc = searcher.doc(scoreDoc.doc);
                // System.out.println("doc" + scoreDoc.doc + " : " + doc.get("id"));
                columns.add(doc.get("id"));
            }
            for(String c : columns) {
                if (columnsXcount.containsKey(c))
                    columnsXcount.put(c, columnsXcount.get(c) + 1);
                else
                    columnsXcount.put(c, 1);
            }
        }
    }

}
