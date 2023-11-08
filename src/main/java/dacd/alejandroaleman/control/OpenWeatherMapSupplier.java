package dacd.alejandroaleman.control;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;




public class OpenWeatherMapSupplier implements WeatherSupplier{
    private File fileWithApiKey;

    public OpenWeatherMapSupplier(File fileWithApiKey) {
        this.fileWithApiKey = fileWithApiKey;
    }

    public List<List<Weather>> get(List<Location> locationList){
        List<List<Weather>> listOfAllWeather = new ArrayList<>();

        for (int i = 0; i < locationList.size(); i++){
            JsonObject weatherData = getWeatherData(locationList.get(i));
            List<Weather> weather = getWeatherInfo(weatherData, locationList.get(i));
            listOfAllWeather.add(weather);
        }
        return listOfAllWeather;
    }

    private List<Weather> getWeatherInfo(JsonObject weatherData, Location location){
        List<Weather> weatherList = new ArrayList<>();
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
            int humidity = objectMain.get("humidity").getAsInt();
            int clouds = objectClouds.get("all").getAsInt();
            double windVelocity = windObject.get("speed").getAsDouble();
            JsonElement date = forecastObject.get("dt_txt");
            System.out.println(date + " DATE AS STRING");
            Instant dateAsInstant = getDateAsInstant(date.getAsString());
            System.out.println(dateAsInstant);
            Weather weather = new Weather(temperature, precipitation, humidity, clouds, windVelocity, location, dateAsInstant);
            System.out.println(forecastObject);
            System.out.println(forecastObject.get("dt_txt"));
            weatherList.add(weather);
            i ++;
        }
        return weatherList;
    }

    private List<JsonObject> getForecast(JsonArray lista){
        List<JsonObject> forecastListAtTwelve = new ArrayList<>();

        int i = 0;
        while(i < lista.size()) {
            JsonObject forecast = (JsonObject) lista.get(i);
            String substring = String.valueOf(forecast.get("dt_txt")).substring(12, 14);

            if (substring.equals("12")) {
                forecastListAtTwelve.add(forecast);
            }
            i++;
        }
        return forecastListAtTwelve;
    }

    private JsonObject getWeatherData (Location location) {
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

    private String getApiKey(){
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(this.fileWithApiKey))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle the error as needed
        }
        return content.toString();
    }


    private Instant getDateAsInstant(String dateTimeString){
        // Cadena de texto en formato "00:00:00"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
        return localDateTime.toInstant(ZoneOffset.UTC);
    }

}
