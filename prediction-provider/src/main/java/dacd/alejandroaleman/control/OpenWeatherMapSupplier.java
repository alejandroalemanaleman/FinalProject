package dacd.alejandroaleman.control;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dacd.alejandroaleman.model.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OpenWeatherMapSupplier implements WeatherSupplier {
    private final String apiKey;

    public OpenWeatherMapSupplier(String apiKey) {
        this.apiKey = apiKey;
    }

    public List<Weather> get(Location location){
            JsonObject weatherData = getOpenWeatherData(location);
            List<Weather> weather = getWeather(weatherData, location);
            return weather;
    }

    private List<Weather> getWeather(JsonObject weatherData, Location location) {
        JsonArray forecastList = weatherData.getAsJsonArray("list");
        List<JsonObject> openWeatherForecastsAtTwelve = getForecasts(forecastList);
        List<Weather> weathers = convertToWeather(openWeatherForecastsAtTwelve, location);
        return weathers;
    }

    private List<Weather> convertToWeather(List<JsonObject> jsonObjects, Location location){
        List<Weather> weatherList = new ArrayList<>();
        int i = 0;
        while (i < jsonObjects.size()) {
            JsonObject forecastObject = jsonObjects.get(i);
            JsonObject objectMain = forecastObject.getAsJsonObject("main");
            JsonObject objectClouds = forecastObject.getAsJsonObject("clouds");
            JsonObject windObject = forecastObject.getAsJsonObject("wind");
            double temperature = objectMain.get("temp").getAsDouble();
            double precipitation = forecastObject.get("pop").getAsDouble();
            int humidity = objectMain.get("humidity").getAsInt();
            int clouds = objectClouds.get("all").getAsInt();
            double windVelocity = windObject.get("speed").getAsDouble();
            JsonElement date = forecastObject.get("dt_txt");
            Instant dateAsInstant = getDateAsInstant(date.getAsString());
            Weather weather = new Weather(temperature, precipitation, humidity, clouds, windVelocity, location, dateAsInstant);
            weatherList.add(weather);
            i ++;
        }
        return weatherList;
    }

    private List<JsonObject> getForecasts(JsonArray forecastList){
        List<JsonObject> forecastListAtTwelve = new ArrayList<>();
        int i = 0;
        while(i < forecastList.size()) {
            JsonObject forecast = (JsonObject) forecastList.get(i);
            String substring = String.valueOf(forecast.get("dt_txt")).substring(12, 14);
            if (substring.equals("12")) {
                forecastListAtTwelve.add(forecast);
            }
            i++;
        }
        return forecastListAtTwelve;
    }

    private JsonObject getOpenWeatherData (Location location) {
        try {
            String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" + location.getLat() + "&lon="+ location.getLon() + "&appid=" + this.apiKey
                    + "&units=metric";
            Document document = Jsoup.connect(apiUrl).ignoreContentType(true).get();
            String jsonResponse = document.text();
            Gson gson = new Gson();
            return gson.fromJson(jsonResponse, JsonObject.class);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong.");
        }
        return null;
    }

    private Instant getDateAsInstant(String dateTimeString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
        return localDateTime.toInstant(ZoneOffset.UTC);
    }
}