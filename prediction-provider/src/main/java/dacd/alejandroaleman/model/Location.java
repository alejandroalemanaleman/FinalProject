package dacd.alejandroaleman.model;

public class Location {
    private final String lat;
    private final String lon;

    public Location(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }
    public String getLon() {
        return lon;
    }

}
