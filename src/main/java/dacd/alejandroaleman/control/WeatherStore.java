package dacd.alejandroaleman.control;

import java.util.List;

public interface WeatherStore {
    void save (List<List<Weather>> listOfAllWeather);
}
