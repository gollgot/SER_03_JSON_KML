package main;

public class Main {

    public static void main(String[] args) {
        JsonToKmlParser parser = new JsonToKmlParser("src/main/resources/countries.geojson", "resources/out.kml");
        parser.convert();
    }

    public static void main(){

    }

}
