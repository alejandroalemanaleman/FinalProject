package dacd.alejandroaleman.model;

import java.time.Instant;

public class Hotel {
    private final Instant ts;
    private final String ss;
    private final String name;
    private final String place;
    private final String pricePerNight;


    public Hotel(String name, String place, String pricePerNight) {
        this.ts = Instant.now();
        this.ss = "Hotel-Provider";
        this.name = name;
        this.place = place;
        this.pricePerNight = pricePerNight;
    }
}
