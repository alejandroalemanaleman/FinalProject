package dacd.alejandroaleman.model;

public class Location {
    private final String island;
    private String city;
    private final String lat;
    private final String lon;


    public Location(String place, String lat, String lon) {
        this.island = place;
        this.lat = lat;
        this.lon = lon;
    }

    public String getIsland() {
        return island;
    }

    public String getLat() {
        return lat;
    }
    public String getLon() {
        return lon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
