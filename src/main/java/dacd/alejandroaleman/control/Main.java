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

    public static void loadWeatherData(){
        WeatherController weatherController = new WeatherController();
        weatherController.getWeather();
    }
    public static void main(String[] args) {

        loadWeatherData();

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
