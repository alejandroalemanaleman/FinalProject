package dacd.alejandroaleman.control;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import dacd.alejandroaleman.model.WeatherData;




public class Main {
    public static void main(String[] args) {
        String result = ObtenerInfo();
        System.out.println(result);
        //WeatherData weatherDataObj = ConvertToWeatherDataObject(result);
        JsonObject weatherDataObj = ConvertToJsonObject(result);
        System.out.println(weatherDataObj.getAsJsonObject("city").get("name"));



    }

    public static String ObtenerInfo() {
        try {
            String apiKey = "";
            String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=44.34&lon=10.99&appid=" + apiKey;

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
}
