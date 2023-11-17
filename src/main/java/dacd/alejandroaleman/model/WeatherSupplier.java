package dacd.alejandroaleman.model;

import java.util.List;

public interface WeatherSupplier {
    List<List<Weather>> get(List<Location> locationList);
}
