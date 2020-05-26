import org.json.simple.JSONArray;

public class Utils {

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
