package lucenex;

import lucenex.index.JSONIndexer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        try {
            InputStream inputStream = Files.newInputStream(Paths.get("tables.json"));
            JSONIndexer.readJsonStream(inputStream);

        }catch (Exception e ){
            System.out.println("Eccezione");
            System.out.println(e.getMessage());
        }

    }




}
