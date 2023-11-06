package dacd.alejandroaleman.control;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public interface WeatherSupplier {
    List<WeatherInfo> getWeatherInfo(JsonObject jsonObject, Location location);
    List<JsonObject> getForecast(JsonArray lista);
    JsonObject getWeatherData (Location location);
    }
