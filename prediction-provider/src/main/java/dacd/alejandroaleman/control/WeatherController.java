package dacd.alejandroaleman.control;

import dacd.alejandroaleman.control.exceptions.StoreException;
import dacd.alejandroaleman.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class WeatherController extends TimerTask {
    private final OpenWeatherMapSupplier openWeatherMapSupplier;
    private final JMSWeatherStore jmsWeatherStore;

    public WeatherController(OpenWeatherMapSupplier openWeatherMapSupplier, JMSWeatherStore jmsWeatherStore) {
        this.openWeatherMapSupplier = openWeatherMapSupplier;
        this.jmsWeatherStore = jmsWeatherStore;
    }

    public void execute(){
        try{
            List<Location> locationList = new ArrayList<>();
            locationList.add(new Location("29.2298950", "-13.5050417", "La Graciosa"));
            locationList.add(new Location("28.9611348", "-13.5512381", "Lanzarote"));
            locationList.add(new Location("28.5010371", "-13.8628859", "Fuerteventura"));
            locationList.add(new Location("28.12281998218409", "-15.427139106449038", "Gran Canaria"));
            locationList.add(new Location("28.466579957829115", "-16.249983979646377", "Tenerife"));
            locationList.add(new Location("28.0914976", "-17.1107147", "La Gomera"));
            locationList.add(new Location("28.6837586", "-17.7645926", "La Palma"));
            locationList.add(new Location("27.810376412061633", "-17.91380238618073", "El Hierro"));

            for (Location location : locationList) {
                List<Weather> weathers = openWeatherMapSupplier.get(location);
                jmsWeatherStore.save(weathers);
            }
        }
        catch (StoreException e){
            throw new RuntimeException(e);
        }
    }
    public void run() {
        execute();
    }
}