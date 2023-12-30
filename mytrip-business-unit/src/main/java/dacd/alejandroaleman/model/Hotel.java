package dacd.alejandroaleman.model;

import java.time.Instant;

public class Hotel {
    private final String name;
    private final String place;
    private final String pricePerNight;


    public Hotel(String name, String place, String pricePerNight) {
        this.name = name;
        this.place = place;
        this.pricePerNight = pricePerNight;
    }

    @Override
    public String toString() {
        return "Hotel: " + name +
                ", pricePerNight=" + pricePerNight;
    }
}
//RATIING