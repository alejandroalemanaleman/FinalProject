package dacd.alejandroaleman.control;

import dacd.alejandroaleman.control.exceptions.StoreException;
import dacd.alejandroaleman.model.Weather;

import java.util.List;

public interface WeatherStore {
    void save (List<Weather> weathers) throws StoreException;
}
