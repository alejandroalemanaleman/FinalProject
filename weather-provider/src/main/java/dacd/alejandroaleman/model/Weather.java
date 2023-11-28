package dacd.alejandroaleman.model;

import java.time.Instant;

public class Weather {

    private final Instant ts;
    private final String ss;
    private final Instant predictionTime;
    private final Location location;
    private final double temperature;
    private final double precipitation;
    private final int humidity;
    private final int clouds;
    private final double windVelocity;


    public Weather(double temperature, double precipitation, int humidity, int clouds, double windVelocity, Location location, Instant forecastDate) {
        this.ts = Instant.now();
        this.ss = "WeatherSupplier";
        this.predictionTime = forecastDate;
        this.location = location;
        this.temperature = temperature;
        this.precipitation = precipitation;
        this.humidity = humidity;
        this.clouds = clouds;
        this.windVelocity = windVelocity;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getClouds() {
        return clouds;
    }

    public double getWindVelocity() {
        return windVelocity;
    }

    public Location getLocation() {
        return location;
    }

    public Instant getTs() {
        return ts;
    }

    public String getSs() {
        return ss;
    }

    public Instant getPredictionTime() {
        return predictionTime;
    }
}

