package dacd.alejandroaleman.control;

import dacd.alejandroaleman.model.Hotel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TripadvisorHotelSupplier implements HotelSupplier{
    private final String apiKey;

    public TripadvisorHotelSupplier(String apiKey) {
        this.apiKey = apiKey;
    }

    public List<Hotel> get(String place) {
        List<String> links = getHotelLinks(place);
        return getHotels(links, place);
    }


    private List<Hotel> getHotels(List<String> links, String place) {
        List<Hotel> hotels = new ArrayList<>();
        for (String link : links) {
            Document doc = null;
            try {
                doc = Jsoup.connect(link).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                String nameHotelSelector = "#HEADING";
                String priceRangeSelector = "#OVERVIEW_SUBSECTION > div:nth-child(1) > div:nth-child(2)";
                Element nombreHotelElement = doc.select(nameHotelSelector).first();
                Element pricerangeElement = doc.select(priceRangeSelector).first();

                // Obtener el nombre del hotel o establecer como "Not Available" si es nulo
                String nameHotel = (nombreHotelElement != null) ? nombreHotelElement.text() : "Not Available";

                // Obtener el rango de precios o establecer como "Not Available" si es nulo
                String priceRangeWithText = (pricerangeElement != null) ? pricerangeElement.text() : "Price not available.";
                String priceRange = (priceRangeWithText.matches(".*\\d+ € - \\d+ €.*")) ?
                        priceRangeWithText.replaceAll(".*?(\\d+ € - \\d+ €).*", "$1") : "Not Available";

                // Agregar el hotel a la lista
                hotels.add(new Hotel(nameHotel, place, priceRange));

                // Imprimir información (opcional)
                System.out.println(nameHotel + " " + place);
            }
        }
        return hotels;
    }


    public List<String> getHotelLinks(String place) {
        List<String> links = new ArrayList<>();
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(place + ".txt")) {
            if (inputStream != null) {
                try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
                    while (scanner.hasNextLine()) {
                        links.add(scanner.nextLine());
                    }
                }
            } else {
                System.err.println("No se pudo encontrar el archivo en el JAR: " + place + ".txt");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return links;
    }




    /*
    public List<Hotel> void get(Location location) {
        JsonObject hotelData = getTripAdvisorHotels(location);
        System.out.println(hotelData);
       getHotels(hotelData, location);
       //return getHotels(hotelData, location);
    }

    public List<Hotel> void getHotels(JsonObject hotelData, Location location){
        JsonArray listOfHotelsJSON = hotelData.getAsJsonObject("data").getAsJsonArray("data");
        List<Hotel> hotels = new ArrayList<Hotel>();
        for (int i = 0; i < listOfHotelsJSON.size(); i++) {
            JsonObject hotelJson = listOfHotelsJSON.get(i).getAsJsonObject();
            String hotelName = ""+ hotelJson.get("title").getAsString().replaceFirst("^\\d+\\.\\s", "");
            System.out.println(hotelName + " PLACE:" + location.getPlace());
            JsonObject pricePerNight = hotelJson.get("price").getAsJsonObject();
            Hotel hotel = new Hotel(hotelName, location.getPlace(), pricePerNight.getAsInt());
        }

    }

    public JsonObject getTripAdvisorHotels(Location location) {
        try {
            OkHttpClient client = new OkHttpClient();

            String apiUrl = "https://tripadvisor16.p.rapidapi.com/api/v1/hotels/searchHotelsByLocation?latitude="+ location.getLat() +"&longitude="+ location.getLon() +"&checkIn=2023-12-28&checkOut=2023-12-27&pageNumber=1&currencyCode=EUR";
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .get()
                    .addHeader("X-RapidAPI-Key", "4c73be2a18mshcefe0220407ac95p14b21fjsn6da25e4915b4")
                    .addHeader("X-RapidAPI-Host", "tripadvisor16.p.rapidapi.com")
                    .build();

            Response response = client.newCall(request).execute();
            String jsonResponse = response.body().string();

            Gson gson = new Gson();
            return gson.fromJson(jsonResponse, JsonObject.class);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in TripadvisorHotelSupplier");
        }
        return null;

}

     */


}
