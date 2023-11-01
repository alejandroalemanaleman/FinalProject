package dacd.alejandroaleman.control;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class OpenWeatherMapSupplier {

    public String ObtenerInfo() {
        try {
            String apiKey = "ca65a040d090abca857edca70284755b";
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
}
