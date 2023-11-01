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
        Location LaGraciosa = new Location("La Graciosa", 29.2298950, -13.5050417);
        Location Lanzarote = new Location("Lanzarote", 28.9611348, -13.5512381);
        Location Fuerteventura = new Location("Fuerteventura", 28.5010371, -13.8628859);
        Location GranCanaria = new Location("GranCanaria", 28.12281998218409, -15.427139106449038);
        Location Tenerife = new Location("Tenerife", 28.466579957829115, -16.249983979646377);
        Location LaGomera = new Location("La Gomera", 28.0914976, -17.1107147);
        Location LaPalma = new Location("La Palma", 28.6837586, -17.7645926);
        Location ElHierro = new Location("El Hierro", 27.810376412061633, -17.91380238618073);



        String result = ObtenerInfo(LaGraciosa);
        System.out.println(result);
        JsonObject weatherDataObj = ConvertToJsonObject(result);
        //WeatherInfo granCanariaWeatherInfo = getWeatherInfo(weatherDataObj, GranCanaria);
        WeatherInfo laGraciosaWeatherInfo = getWeatherInfo(weatherDataObj, LaGraciosa);
        System.out.println(laGraciosaWeatherInfo.getClouds() + " "+laGraciosaWeatherInfo.getHourChecked().toString());

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
