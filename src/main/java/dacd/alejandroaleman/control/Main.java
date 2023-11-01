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

    public static List<WeatherInfo> loadWeatherData(){
        WeatherController weatherController = new WeatherController();
        return weatherController.getWeather();
    }

    public static void saveWeatherData(){
        return;
    }

    public static void main(String[] args) {

        List<WeatherInfo> weatherInfoList = loadWeatherData();




    }






}
