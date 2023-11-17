package dacd.alejandroaleman.model;

import java.util.List;

public interface WeatherStore {
    void save (List<List<Weather>> listOfAllWeather);
}
