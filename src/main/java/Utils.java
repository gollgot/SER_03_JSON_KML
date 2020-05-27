import org.json.simple.JSONArray;

public class Utils {

    /**
     * transforme un JSONArray de coordonnées en une chaîne de caractères contenant les mêmes coordonnées,
     * formatées pour du KML
     * @param outerBoundaries JSONArray à convertir en String
     * @return la chaîne de caractères générée
     */
    public static String convertOuterBoundariesToStringCoordinates(JSONArray outerBoundaries){
        StringBuilder result = new StringBuilder();
        for(Object coordinatesObject : outerBoundaries){
            JSONArray coordinates = (JSONArray) coordinatesObject;
            double coord1 = (double) coordinates.get(0);
            double coord2 = (double) coordinates.get(1);
            result.append(coord1).append(",").append(coord2).append(" ");
        }

        return result.toString();
    }

}
