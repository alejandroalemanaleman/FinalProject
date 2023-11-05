package dacd.alejandroaleman.control;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalTime;

public interface WeatherSupplier {
    WeatherInfo getWeatherInfo(JsonObject jsonObject, Location location);
    JsonObject getForecast(JsonArray lista);
    JsonObject getWeatherData (Location location);
    }
