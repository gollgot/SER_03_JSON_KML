/*
 * Auteurs: Loïc Dessaules & Gildas Houlmann
 * Fichier: KMLGenerator.java
 * Date: 29.05.2020
 */

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.json.simple.JSONArray;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class KMLGenerator {

    private String outputFile;
    private ArrayList<Country> countries;

    /**
     * Constructeur prenant le chemin du fichier à créer et une liste de pays en paramètre
     * @param outputFile le chemin du fichier à créer
     * @param countries la liste des pays à mettre dans le fichier
     */
    public KMLGenerator(String outputFile, ArrayList<Country> countries) {
        this.outputFile = outputFile;
        this.countries = countries;
    }

    /**
     * Génère le fichier kml à partir des pays donnés dans le constructeur
     */
    public void generateOutput(){
        try {
            // Le 1er element créé est automatiquement le root element
            // l'URI est le namespace, pour chaque Element, il faudra donner le meme namespace
            // que notre element root.
            Element kml = new Element("kml", "http://www.opengis.net/kml/2.2");
            Document JDOM2document = new Document(kml);

            // Document
            Element document = new Element("Document", kml.getNamespace());

            // Style
            Element style = new Element("Style", kml.getNamespace());
            style.setAttribute(new Attribute("id", "country-style"));
            style.addContent(new Element("PolyStyle", kml.getNamespace()).
                    addContent(new Element("fill", kml.getNamespace())
                    .setText("0")));
            document.addContent(style);

            // Génère les pays
            for(Country country : countries){
                Element placeMark = new Element("Placemark", kml.getNamespace());
                placeMark.addContent(new Element("name", kml.getNamespace()).setText(country.getName()));
                placeMark.addContent(new Element("styleUrl", kml.getNamespace()).setText("country-style"));

                Element polygon;

                //Génère les coordonnées selon le type de géomètrie du pays
                switch (country.getGeometryType()){
                    case Polygon:
                        polygon = generatePolygon(country.getOuterBoundariesList().get(0), kml);
                        placeMark.addContent(polygon);
                        break;
                    case MultiPolygon:
                        Element multiGeometry = new Element("MultiGeometry", kml.getNamespace());
                        //génère
                        for(JSONArray outerBoundary : country.getOuterBoundariesList()){
                            polygon = generatePolygon(outerBoundary, kml);
                            multiGeometry.addContent(polygon);
                        }
                        placeMark.addContent(multiGeometry);
                        break;
                }
                document.addContent(placeMark);
            }

            JDOM2document.getRootElement().addContent(document);

            // On genere l'output
            XMLOutputter xmlOutputter = new XMLOutputter();
            xmlOutputter.setFormat(Format.getPrettyFormat());
            xmlOutputter.output(JDOM2document, new FileWriter(outputFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Element generatePolygon(JSONArray outerBoundaries,Element kml){
        Element coordinates =
                new Element("coordinates", kml.getNamespace()).
                        setText(Utils.convertOuterBoundariesToStringCoordinates(
                                outerBoundaries)
                        );

        return new Element("Polygon", kml.getNamespace())
                    .addContent(new Element("outerBoundaryIs", kml.getNamespace())
                    .addContent(new Element("LinearRing", kml.getNamespace())
                    .addContent(coordinates)));
    }

}
