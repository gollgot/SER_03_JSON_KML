import org.json.simple.JSONArray;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        final String INPUT_FILE = "src/main/resources/countries.geojson";
        final String OUTPUT_FILE = "src/main/resources/countries.kml";

        GeojsonParser parser = new GeojsonParser(INPUT_FILE);
        ArrayList<Country> countries = parser.generateCountries();

        KMLGenerator kmlGenerator = new KMLGenerator(OUTPUT_FILE, countries);
        kmlGenerator.generate();
    }

}
