package dacd.alejandroaleman.model;

public class Location {
    private final String lat;
    private final String lon;
    private final String place;

    public Location(String lat, String lon, String place) {
        this.lat = lat;
        this.lon = lon;
        this.place = place;
    }

    public String getLat() {
        return lat;
    }
    public String getLon() {
        return lon;
    }
    public String getPlace() {return place;}

}