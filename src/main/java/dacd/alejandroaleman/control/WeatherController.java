package dacd.alejandroaleman.control;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class WeatherController {
    public List<List<WeatherInfo>> execute(){
        List<Location> locationList = new ArrayList<>();
        List<List<WeatherInfo>> weatherInfoOfAll = new ArrayList<>(); //LISTA FINAL


        locationList.add(new Location("LaGraciosa", 29.2298950, -13.5050417));
        locationList.add(new Location("Lanzarote", 28.9611348, -13.5512381));
        locationList.add(new Location("Fuerteventura", 28.5010371, -13.8628859));
        locationList.add(new Location("GranCanaria", 28.12281998218409, -15.427139106449038));
        locationList.add(new Location("Tenerife", 28.466579957829115, -16.249983979646377));
        locationList.add(new Location("LaGomera", 28.0914976, -17.1107147));
        locationList.add(new Location("LaPalma", 28.6837586, -17.7645926));
        locationList.add(new Location("ElHierro", 27.810376412061633, -17.91380238618073));

        OpenWeatherMapSupplier openWeatherMapSupplier = new OpenWeatherMapSupplier();

        for (int i = 0; i < locationList.size(); i++){
            JsonObject weatherData = openWeatherMapSupplier.getWeatherData(locationList.get(i));
            List<WeatherInfo> weatherInfo = openWeatherMapSupplier.getWeatherInfo(weatherData, locationList.get(i));
            weatherInfoOfAll.add(weatherInfo);

            /*
            System.out.println(weatherInfo.get().getLocation().getCity());
            System.out.println(weatherInfo.get(i).toString());
             */

            //weatherInfoOfAll.add(i, weatherInfo);

            /*
            for (int i = 0; i < locationList.size(); i++){
                recorrer lista de listas! ^^
            }
             */
        }
        return weatherInfoOfAll;
    }
}
