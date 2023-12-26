package dacd.alejandroaleman.control;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dacd.alejandroaleman.model.Hotel;
import dacd.alejandroaleman.model.Location;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;

public class TripadvisorHotelSupplier implements HotelSupplier{
    private final String apiKey;

    public TripadvisorHotelSupplier(String apiKey) {
        this.apiKey = apiKey;
    }

    public /*List<Hotel>*/ void get(Location location) {
        JsonObject hotelData = getTripAdvisorHotels(location);
        System.out.println(hotelData);
       getHotels(hotelData, location);
       //return getHotels(hotelData, location);
    }

    public /*List<Hotel>*/ void getHotels(JsonObject hotelData, Location location){
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
}
