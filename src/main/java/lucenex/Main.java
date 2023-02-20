package lucenex;

import lucenex.index.JSONIndexer;
import lucenex.query.QueryManager;
import org.apache.lucene.codecs.simpletext.SimpleTextCodec;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
//            InputStream inputStream = Files.newInputStream(Paths.get("sample.json"));
            InputStream inputStream = Files.newInputStream(Paths.get("tables.json"));
            JSONIndexer.readJsonStream(inputStream, null);
//            Map<String, Integer> result = QueryManager.mergeList(4, new String[]{"Ajax", "Napoli", "Alcaraz", "Rangers"});
//            List<String> lista = List.of(new String[]{
//                    "abbagliante",
//                    "abbandonato",
//                    "abbastanza",
//                    "abbattimento",
//                    "abbigliamento",
//                    "abbinamento",
//                    "abbonamento",
//                    "abbraccio",
//                    "abc",
//                    "abete",
//                    "abisso",
//                    "abitante",
//                    "abitudine",
//                    "accademia",
//                    "accampamento",
//                    "accanimento",
//                    "accappatoio",
//                    "accedere",
//                    "acceleratore",
//                    "accensione",
//                    "accesso",
//                    "accessorio",
//                    "accettare",
//                    "acciaio",
//                    "accidentale",
//                    "acclamazione",
//                    "accompagnamento",
//                    "accordo",
//                    "accrescere",
//                    "accusare",
//                    "acetone",
//                    "achille",
//                    "acido",
//                    "acqua",
//                    "acquario",
//                    "acquistare",
//                    "acre",
//                    "acrilico",
//                    "acrobatico",
//                    "acronimo",
//                    "acustico",
//                    "adattatore",
//                    "addebitare",
//                    "addetto",
//                    "addio",
//                    "addizione",
//                    "adeguare",
//                    "aderente",
//                    "adescare",
//                    "adiacente",
//                    "adiposo",
//                    "admiral",
//                    "adoperare",
//                    "adottare",
//                    "adozione",
//                    "adrenalina",
//                    "adulto",
//                    "aereo",
//                    "aerobica",
//                    "aeroplano",
//                    "affamato",
//                    "affare",
//                    "affermazione",
//                    "affettare",
//                    "affezionare",
//                    "affidabile",
//                    "affilare",
//                    "affittare",
//                    "affluente",
//                    "affogare",
//                    "affrontare",
//                    "africano",
//                    "agave",
//                    "agente",
//                    "aggettivo",
//                    "agganciare",
//                    "aggeggio",
//                    "aggiornamento",
//                    "aggiungere",
//                    "aggrapparsi",
//                    "aggressione",
//                    "agire",
//                    "agitare",
//                    "agonia",
//                    "agricolo",
//                    "agrumeto",
//                    "aguzzo",
//                    "aiuola",
//                    "alba",
//                    "albergo",
//                    "albero",
//                    "albicocca",
//                    "album",
//                    "alce",
//                    "alchimia",
//                    "alcool",
//                    "alettone",
//                    "alfa",
//                    "algebra",
//                    "aliante",
//                    "alimentare",
//                    "alito",
//                    "alla",
//                    "alleanza",
//                    "alleato",
//                    "allegare",
//                    "allevamento",
//                    "allievo",
//                    "allineare",
//                    "allodola",
//                    "allontanare",
//                    "alluce",
//                    "alluminio",
//                    "almeno",
//                    "alpaca",
//                    "alpestre",
//                    "altalena",
//                    "alterare",
//                    "altopiano",
//                    "altro",
//                    "alveare",
//                    "amante",
//                    "amarezza",
//                    "ambasciata",
//                    "ambiente",
//                    "ambizione",
//                    "ambulanza",
//                    "ametista",
//                    "amico",
//                    "ammacc"
//            });lista.subList(0,2).toArray(new String[0])
            Map<String, Integer> result = QueryManager.mergeList(4, new String[]{"prova"});
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
