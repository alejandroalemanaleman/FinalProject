package dacd.alejandroaleman.model;

import java.time.Instant;
import java.util.List;

public class Event {
    private final String ts;
    private final String ss;
    private final String predictionTime;
    private final Location location;
    private List<Weather> weathers;

    public Event(Location location, List<Weather> weathers) {
        this.ts = Instant.now().toString();
        this.ss = "OpenWeatherMap";
        this.location = location;
        this.weathers = weathers;
        this.predictionTime = "12:00:00";
    }

    public String getTs() {
        return ts;
    }

    public String getSs() {
        return ss;
    }

    public List<Weather> getWeatherList() {
        return weathers;
    }
}
