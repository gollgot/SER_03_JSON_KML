import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GeojsonParser {

    private String jsonFilePath;
    private ArrayList<Country> countries;

    public GeojsonParser(String jsonFilePath){
        this.jsonFilePath = jsonFilePath;
        this.countries = new ArrayList<>();
    }

    public ArrayList<Country> generateCountries () {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(jsonFilePath)) {

            Object obj = jsonParser.parse(reader);
            JSONObject root = (JSONObject) obj;

            JSONArray features = (JSONArray) root.get("features");

            //Une feature = un pays
            features.forEach(feature->generateCountry((JSONObject) feature));
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return countries;
    }

    //Lit et convertit un pays
    private void generateCountry(JSONObject feature){
        /* Lecture des propriétés */
        JSONObject properties = (JSONObject) feature.get("properties");

        String name = (String)properties.get("ADMIN");
        String iso3 = (String)properties.get("ISO_A3");

        /* Lecture de la géométrie*/
        JSONObject geometry = (JSONObject) feature.get("geometry");
        String geometryType = (String) geometry.get("type");
        JSONArray coordinates = (JSONArray) geometry.get("coordinates");

        // On affiche en debug le nom du pays qui est parsé
        System.out.println("(" + iso3 + ") " + name);

        // On créer notre objet pays
        Country country = new Country(name, iso3, GeometryType.valueOf(geometryType));

        // Polygon
        if(geometryType.equals("Polygon")){
            JSONArray outerBoundary = (JSONArray)(coordinates.get(0));
            country.addCoordinates(outerBoundary);
            System.out.println("    - " + outerBoundary.size() + " coordinates");
        }
        // Multi polygon
        else if(geometryType.equals("MultiPolygon")){
            for(Object polygonObject : coordinates){
                JSONArray polygon = (JSONArray) polygonObject;
                JSONArray outerBoundary = (JSONArray)(polygon.get(0));
                country.addCoordinates(outerBoundary);
                System.out.println("    - " + outerBoundary.size() + " coordinates");
            }
        }

        countries.add(country);
    }
}
