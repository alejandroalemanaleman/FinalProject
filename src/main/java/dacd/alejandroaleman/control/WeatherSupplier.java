package dacd.alejandroaleman.control;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalTime;

public class WeatherSupplier {
    public WeatherInfo getWeatherInfo(JsonObject jsonObject, Location location){

        String city = String.valueOf(jsonObject.getAsJsonObject("city").get("name"));
        JsonArray forecastList = jsonObject.getAsJsonArray("list");
        JsonObject forecastObject = (JsonObject) forecastList.get(13);
        JsonObject objectMain = forecastObject.getAsJsonObject("main");
        JsonObject objectClouds = forecastObject.getAsJsonObject("clouds");
        JsonObject windObject = forecastObject.getAsJsonObject("wind");

        double temperature = objectMain.get("temp").getAsDouble();
        double precipitation = forecastObject.get("pop").getAsDouble();
        double humidity = objectMain.get("humidity").getAsDouble();
        double clouds = objectClouds.get("all").getAsDouble();
        double windVelocity = windObject.get("speed").getAsDouble();

        LocalTime currentTime = LocalTime.now();
        location.setCity(String.valueOf(jsonObject.getAsJsonObject("city").get("name")));
        WeatherInfo weatherInfo = new WeatherInfo(temperature, precipitation, humidity, clouds, windVelocity, location);
        System.out.println(city + temperature+precipitation+humidity);
        System.out.println(forecastObject);
        System.out.println(forecastObject.get("dt_txt"));

        return weatherInfo;
    }
}
