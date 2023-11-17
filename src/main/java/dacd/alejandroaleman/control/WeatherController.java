package dacd.alejandroaleman.control;

import dacd.alejandroaleman.model.Location;
import dacd.alejandroaleman.model.Weather;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;


public class WeatherController extends TimerTask {
    private final OpenWeatherMapSupplier openWeatherMapSupplier;
    private final SQLiteWeatherStore sqLiteWeatherStore;

    public WeatherController(OpenWeatherMapSupplier openWeatherMapSupplier, SQLiteWeatherStore sqLiteWeatherStore) {
        this.openWeatherMapSupplier = openWeatherMapSupplier;
        this.sqLiteWeatherStore = sqLiteWeatherStore;
    }

    public void execute(){
        List<Location> locationList = new ArrayList<>();

        locationList.add(new Location("LaGraciosa", "29.2298950", "-13.5050417"));
        locationList.add(new Location("Lanzarote", "28.9611348", "-13.5512381"));
        locationList.add(new Location("Fuerteventura", "28.5010371", "-13.8628859"));
        locationList.add(new Location("GranCanaria", "28.12281998218409", "-15.427139106449038"));
        locationList.add(new Location("Tenerife", "28.466579957829115", "-16.249983979646377"));
        locationList.add(new Location("LaGomera", "28.0914976", "-17.1107147"));
        locationList.add(new Location("LaPalma", "28.6837586", "-17.7645926"));
        locationList.add(new Location("ElHierro", "27.810376412061633", "-17.91380238618073"));

        List<List<Weather>> listOfAllWeather = this.openWeatherMapSupplier.get(locationList);
        sqLiteWeatherStore.save(listOfAllWeather);

    }

    public void run() {
        execute();
    }
}
