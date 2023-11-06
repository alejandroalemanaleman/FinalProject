package dacd.alejandroaleman.control;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;


public class OpenWeatherMapSupplier implements WeatherSupplier{

    int forecastNum = 0;

    public List<WeatherInfo> getWeatherInfo(JsonObject weatherData, Location location){
        List<WeatherInfo> weatherInfoList = new ArrayList<>();
        location.setCity(String.valueOf(weatherData.getAsJsonObject("city").get("name")));
        JsonArray forecastList = weatherData.getAsJsonArray("list");
        List<JsonObject> forecastAtTwelve = getForecast(forecastList);

        int i = 0;
        while (i < forecastAtTwelve.size()) {
            //System.out.println(forecastList);

            // TODO implementar bucle que encuentre primera previsión que sea a las 12:00.
            // y hacer uso de las interfaces.
            JsonObject forecastObject = forecastAtTwelve.get(i);


            JsonObject objectMain = forecastObject.getAsJsonObject("main");
            JsonObject objectClouds = forecastObject.getAsJsonObject("clouds");
            JsonObject windObject = forecastObject.getAsJsonObject("wind");

            double temperature = objectMain.get("temp").getAsDouble();
            double precipitation = forecastObject.get("pop").getAsDouble();
            double humidity = objectMain.get("humidity").getAsDouble();
            double clouds = objectClouds.get("all").getAsDouble();
            double windVelocity = windObject.get("speed").getAsDouble();
            String date = String.valueOf(forecastObject.get("dt_txt"));

            WeatherInfo weatherInfo = new WeatherInfo(temperature, precipitation, humidity, clouds, windVelocity, location, date);
            System.out.println(forecastObject);
            System.out.println(forecastObject.get("dt_txt"));
            weatherInfoList.add(weatherInfo);
            i ++;
        }
        return weatherInfoList;
    }

    public List<JsonObject> getForecast(JsonArray lista){
        List<JsonObject> forecastListAtTwelve = new ArrayList<>();

        int i = 0;
        while(i < lista.size()) {
            JsonObject forecast = (JsonObject) lista.get(i);
            String substring = String.valueOf(forecast.get("dt_txt")).substring(12, 14);

            if (substring.equals("00")) {
                forecastListAtTwelve.add(forecast);
            }
            i++;
        }
        return forecastListAtTwelve;
    }

    public JsonObject getWeatherData (Location location) {
        try {
            String apiKey = "ca65a040d090abca857edca70284755b";
            String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" + location.getLat() + "&lon="+ location.getLon() + "&appid=" + apiKey;

            // Realiza una solicitud HTTP a la URL
            Document document = Jsoup.connect(apiUrl).ignoreContentType(true).get();

            // Obtiene el contenido JSON de la respuesta
            String jsonResponse = document.text();

            Gson gson = new Gson();
            //WeatherData weatherData = gson.fromJson(jsonText, WeatherData.class);
            return gson.fromJson(jsonResponse, JsonObject.class);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong.");
        }
        return null;
    }

}
