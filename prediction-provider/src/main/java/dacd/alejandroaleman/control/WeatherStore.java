package dacd.alejandroaleman.control;

import dacd.alejandroaleman.exceptions.StoreException;
import dacd.alejandroaleman.model.Weather;

import javax.jms.JMSException;
import java.util.List;

public interface WeatherStore {
    void save (List<Weather> weathers) throws StoreException;
}
