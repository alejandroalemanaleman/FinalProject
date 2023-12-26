package dacd.alejandroaleman.control;

import dacd.alejandroaleman.model.Location;
import dacd.alejandroaleman.model.Weather;

import java.util.List;

public interface WeatherSupplier {
    List<Weather> get(Location location);
}
