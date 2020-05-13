package main;

public class Main {

    public void main(){
        JsonToKmlParser parser = new JsonToKmlParser("resources/countries.json", "resources/out.kml");
        parser.convert();
    }

}
