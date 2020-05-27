import org.json.simple.JSONArray;

import java.util.ArrayList;

public class Country {
    private String name;
    private String iso3;
    private GeometryType geometryType;
    private ArrayList<JSONArray> outerBoundariesList;

    /**
     * Constructeur
     * @param name nom du pays
     * @param iso3 acronyme ISO-3 du pays
     * @param geometryType type de géomètrie du pays
     */
    public Country(String name, String iso3, GeometryType geometryType) {
        this.name = name;
        this.iso3 = iso3;
        this.geometryType = geometryType;
        outerBoundariesList = new ArrayList<>();
    }

    /**
     * Ajoute les coordonnées des frontières du pays
     * @param coordinates les coordonnées
     */
    public void addCoordinates(JSONArray coordinates) {
        this.outerBoundariesList.add(coordinates);
    }

    /**
     * @return le nom du pays
     */
    public String getName() {
        return name;
    }

    /**
     * Obtient l'acronyme ISO-3 du pays. Cette donnée n'est pas nécéssaire à la transformation en KML,
     * mais cette classe se veut réutilisable pour d'autres éventuelles utilisations nécessitant cette information
     * @return l'acronyme ISO-3 du pays
     */
    public String getIso3() {
        return iso3;
    }

    /**
     * Obtient le type de géomètrie du pays. Ici, cela peut être soit un Polygon, soit un MultiPolygon
     * @return le type de géomètrie du pays
     */
    public GeometryType getGeometryType() {
        return geometryType;
    }

    /**
     * Obtient les frontières du pays sous forme d'une liste de suites de points formant une boucle.
     * Dans le cas d'un Polygon simple, cette liste n'admet qu'un seul élément.
     * Dans le cas d'un MultiPolygon, chaque élément correspond à une boucle.
     * @return La liste des boucles formant les frontières du pays
     */
    public ArrayList<JSONArray> getOuterBoundariesList() {
        return outerBoundariesList;
    }

}
