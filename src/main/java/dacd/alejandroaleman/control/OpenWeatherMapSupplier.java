package dacd.alejandroaleman.control;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class OpenWeatherMapSupplier {

    public JsonObject ObtenerInfo (Location location) {
        try {
            String apiKey = "ca65a040d090abca857edca70284755b";
            String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" + location.getLat() + "&lon="+ location.getLon() + "&appid=" + apiKey;

            // Realiza una solicitud HTTP a la URL
            Document document = Jsoup.connect(apiUrl).ignoreContentType(true).get();

            // Obtiene el contenido JSON de la respuesta
            String jsonResponse = document.text();

            Gson gson = new Gson();
            //WeatherData weatherData = gson.fromJson(jsonText, WeatherData.class);
            JsonObject weatherDataObj = gson.fromJson(jsonResponse, JsonObject.class);
            return weatherDataObj;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong.");
        }
        return null;
    }
}
