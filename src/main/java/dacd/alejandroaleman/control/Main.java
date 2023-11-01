package dacd.alejandroaleman.control;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import dacd.alejandroaleman.model.WeatherData;
import java.time.LocalTime;
import java.util.SortedMap;


public class Main {
    public static void main(String[] args) {
        Location LaGraciosa = new Location("Santa Cruz de Tenerife", 28.466579957829115, -16.249983979646377);
        Location Lanzarote = new Location("Santa Cruz de Tenerife", 28.466579957829115, -16.249983979646377);
        Location Fuerteventura = new Location("Santa Cruz de Tenerife", 28.466579957829115, -16.249983979646377);
        Location GranCanaria = new Location("Las Palmas", 28.12281998218409, -15.427139106449038);
        Location Tenerife = new Location("Santa Cruz de Tenerife", 28.466579957829115, -16.249983979646377);
        Location LaGomera = new Location("Santa Cruz de Tenerife", 28.466579957829115, -16.249983979646377);
        Location LaPalma = new Location("Santa Cruz de Tenerife", 28.466579957829115, -16.249983979646377);
        Location ElHierro = new Location("Santa Cruz de Tenerife", 28.466579957829115, -16.249983979646377);


        String result = ObtenerInfo(GranCanaria);
        System.out.println(result);
        JsonObject weatherDataObj = ConvertToJsonObject(result);
        WeatherInfo granCanariaWeatherInfo = getWeatherInfo(weatherDataObj, GranCanaria);
        System.out.println(granCanariaWeatherInfo.getClouds() + " "+granCanariaWeatherInfo.getHourChecked().toString());

        /*
        //WeatherData weatherDataObj = ConvertToWeatherDataObject(result);
        JsonObject weatherDataObj = ConvertToJsonObject(result);
        System.out.println(weatherDataObj.getAsJsonObject("city").get("name"));
        JsonArray ForecastList = weatherDataObj.getAsJsonArray("list");
        JsonObject ForecastObject = (JsonObject) ForecastList.get(13);
        System.out.println(ForecastObject.get("dt_txt"));

         */




    }

    public static String ObtenerInfo(Location location) {
        try {
            String apiKey = "ca65a040d090abca857edca70284755b";
            String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" + location.getLat() + "&lon="+ location.getLon() + "&appid=" + apiKey;

            // Realiza una solicitud HTTP a la URL
            Document document = Jsoup.connect(apiUrl).ignoreContentType(true).get();

            // Obtiene el contenido JSON de la respuesta
            String jsonResponse = document.text();

            return jsonResponse;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong.");
        }
        return null;
    }

    public static JsonObject ConvertToJsonObject(String jsonText){
        Gson gson = new Gson();
        //WeatherData weatherData = gson.fromJson(jsonText, WeatherData.class);
        JsonObject weatherDataObj = gson.fromJson(jsonText, JsonObject.class);
        return weatherDataObj;
    }

    public static WeatherInfo getWeatherInfo(JsonObject jsonObject, Location location){

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

        WeatherInfo weatherInfo = new WeatherInfo(temperature, precipitation, humidity, clouds, windVelocity, location);
        System.out.println(city + temperature+precipitation+humidity);
        System.out.println(forecastObject);
        System.out.println(forecastObject.get("dt_txt"));

        return weatherInfo;
    }
}
