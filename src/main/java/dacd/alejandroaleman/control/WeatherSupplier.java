package dacd.alejandroaleman.control;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.Instant;
import java.util.List;

public interface WeatherSupplier {
/*
    WeatherInfo get(Location location, Instant instant);
    List<WeatherInfo> get(Location location, List<Instant> instant);

 */
    List<WeatherInfo> getWeatherInfo(JsonObject jsonObject, Location location);
    List<JsonObject> getForecast(JsonArray lista);
    JsonObject getWeatherData (Location location);
    }
