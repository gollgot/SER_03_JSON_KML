package main;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonToKmlParser {

    private String jsonFilePath;
    private String outputKMLFilePath;

    public JsonToKmlParser(String jsonFilePath, String outputKMLFilePath){
        this.jsonFilePath = jsonFilePath;
        this.outputKMLFilePath = outputKMLFilePath;
    }

    public void convert() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(jsonFilePath)) {

            Object obj = jsonParser.parse(reader);
            JSONArray root = (JSONArray) obj;

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
