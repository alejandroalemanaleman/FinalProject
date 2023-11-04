package dacd.alejandroaleman.control;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalTime;

public class WeatherSupplier {
    public WeatherInfo getWeatherInfo(JsonObject jsonObject, Location location){

        String city = String.valueOf(jsonObject.getAsJsonObject("city").get("name"));
        JsonArray forecastList = jsonObject.getAsJsonArray("list");
        System.out.println(forecastList);

        // TODO implementar bucle que encuentre primera previcón que sea a las 12:00.
        // y hacer uso de las interfaces.

        JsonObject forecastObject = getForecast(forecastList);


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
        System.out.println(forecastObject);
        System.out.println(forecastObject.get("dt_txt"));

        return weatherInfo;
    }

    public JsonObject getForecast(JsonArray lista){

        for (int i = 0; i < lista.size(); i++){
            JsonObject forecast = (JsonObject) lista.get(i);
            String date = String.valueOf(forecast.get("dt_txt"));
            String substring = date.substring(12, 14);
            if (substring.equals("00")){
                return forecast;
            }
        }
        return null;
    }

}
