import org.json.simple.JSONArray;

import java.util.ArrayList;

public class Country {
    private String name;
    private String iso3;
    private GeometryType geometryType;
    private ArrayList<JSONArray> outerBoundariesList;

    public Country(String name, String iso3, GeometryType geometryType) {
        this.name = name;
        this.iso3 = iso3;
        this.geometryType = geometryType;
        outerBoundariesList = new ArrayList<>();
    }

    public void addCoordinates(JSONArray coordinates) {
        this.outerBoundariesList.add(coordinates);
    }

    public String getName() {
        return name;
    }

    public String getIso3() {
        return iso3;
    }

    public GeometryType getGeometryType() {
        return geometryType;
    }

    public ArrayList<JSONArray> getOuterBoundariesList() {
        return outerBoundariesList;
    }

}
