package main;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonToKmlParser {

    private int i = 0;
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
            JSONObject root = (JSONObject) obj;

            JSONArray features = (JSONArray) root.get("features");

            //Une feature = un pays
            features.forEach(feature->convertFeature((JSONObject) feature));
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    //Lit et convertit un pays
    private void convertFeature(JSONObject feature){

        /* Lecture des propriétés */
        JSONObject properties = (JSONObject) feature.get("properties");
        String admin = (String)properties.get("ADMIN");
        String iso_a3 = (String)properties.get("ISO_A3");

        /* Lecture de la géométrie*/
        JSONObject geometry = (JSONObject) feature.get("geometry");
        String geometryType = (String) geometry.get("type");
        JSONArray coordinates = (JSONArray) geometry.get("coordinates");

        if(geometryType.equals("Polygon")){
            JSONArray lineStrings = (JSONArray)(coordinates.get(0));
            JSONArray outerBoundary = (JSONArray)(lineStrings.get(0));
            //lineStrings[1-fin] sont des innerBoundaries
        }else if(geometryType.equals("MultiPolygon")){
            //TODO
        }
    }
}
