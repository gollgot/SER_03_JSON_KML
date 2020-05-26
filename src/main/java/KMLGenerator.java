import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.json.simple.JSONArray;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class KMLGenerator {

    private String outputFile;
    private ArrayList<Country> countries;

    public KMLGenerator(String outputFile, ArrayList<Country> countries) {
        this.outputFile = outputFile;
        this.countries = countries;
    }

    public void generate(){
        try {
            // Le 1er element créer est automatiquement le root element
            Element kml = new Element("kml", "http://www.opengis.net/kml/2.2");
            Document JDOM2document = new Document(kml);

            // Document
            Element document = new Element("Document");

            // Style
            Element style = new Element("Style");
            style.setAttribute(new Attribute("id", "country-style"));
            style.addContent(new Element("PolyStyle").addContent(new Element("fill").setText("0")));
            document.addContent(style);

            // Placemark
            for(Country country : countries){
                Element placeMark = new Element("Placemark");
                placeMark.addContent(new Element("name").setText(country.getName()));
                placeMark.addContent(new Element("styleUrl").setText("country-style"));

                Element polygon;

                switch (country.getGeometryType()){
                    case Polygon:
                        polygon = generatePolygon(country.getOuterBoundariesList().get(0));
                        placeMark.addContent(polygon);
                        break;
                    case MultiPolygon:
                        Element multiGeometry = new Element("MultiGeometry");
                        for(JSONArray outerBoundary : country.getOuterBoundariesList()){
                            polygon = generatePolygon(outerBoundary);
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

    private Element generatePolygon(JSONArray outerBoundaries){
        Element coordinates =
                new Element("coordinates").
                        setText(Utils.convertOuterBoundariesToStringCoordinates(
                                outerBoundaries)
                        );
        Element polygon = new Element("Polygon").addContent(new Element("outerBoundaryIs").addContent(new Element("LinearRing").addContent(coordinates)));

        return polygon;
    }

}
