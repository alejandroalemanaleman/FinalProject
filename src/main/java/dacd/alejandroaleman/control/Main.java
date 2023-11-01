package dacd.alejandroaleman.control;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;


public class Main {
    public static void main(String[] args) {
        List<Location> locationList = new ArrayList<>();
        locationList.add(new Location("La Graciosa", 29.2298950, -13.5050417));
        locationList.add(new Location("Lanzarote", 28.9611348, -13.5512381));
        locationList.add(new Location("Fuerteventura", 28.5010371, -13.8628859));
        locationList.add(new Location("GranCanaria", 28.12281998218409, -15.427139106449038));
        locationList.add(new Location("Tenerife", 28.466579957829115, -16.249983979646377));
        locationList.add(new Location("La Gomera", 28.0914976, -17.1107147));
        locationList.add(new Location("La Palma", 28.6837586, -17.7645926));
        locationList.add(new Location("El Hierro", 27.810376412061633, -17.91380238618073));

        for (int i = 0; i < locationList.size(); i++){
            OpenWeatherMapSupplier openWeatherMapSupplier = new OpenWeatherMapSupplier();
            JsonObject object = openWeatherMapSupplier.ObtenerInfo(locationList.get(i));
            WeatherSupplier weatherSupplier = new WeatherSupplier();
            WeatherInfo weatherInfo = weatherSupplier.getWeatherInfo(object, locationList.get(i));
            System.out.println(weatherInfo.getLocation().getCity());
        }

        /*
        //WeatherData weatherDataObj = ConvertToWeatherDataObject(result);
        JsonObject weatherDataObj = ConvertToJsonObject(result);
        System.out.println(weatherDataObj.getAsJsonObject("city").get("name"));
        JsonArray ForecastList = weatherDataObj.getAsJsonArray("list");
        JsonObject ForecastObject = (JsonObject) ForecastList.get(13);
        System.out.println(ForecastObject.get("dt_txt"));

         */

    }






}
