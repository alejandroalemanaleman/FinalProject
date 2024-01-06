package dacd.alejandroaleman.model;

import java.time.Instant;

public class Hotel {
    private final Instant ts;
    private final String ss;
    private final String name;
    private final String place;
    private final String priceRangePerNight;
    private final String rating;


    public Hotel(String name, String place, String priceRangePerNight, String rating) {
        this.ts = Instant.now();
        this.ss = "Hotel-Provider";
        this.name = name;
        this.place = place;
        this.priceRangePerNight = priceRangePerNight;
        this.rating = rating;
    }
}
