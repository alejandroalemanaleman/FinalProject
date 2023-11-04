package dacd.alejandroaleman.control;

public class Location {
    //GranCanaria, Lanzarote, Fuerteventura, LaGraciosa, Tenerife, LaPalma, LaGomera, ElHierro
    private String place;
    private String city;
    private double lat;
    private double lon;


    public Location(String place, double lat, double lon) {
        this.place = place;
        this.lat = lat;
        this.lon = lon;
    }

    public String getPlace() {
        return place;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}