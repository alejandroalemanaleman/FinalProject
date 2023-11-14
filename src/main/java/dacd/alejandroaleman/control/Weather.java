package dacd.alejandroaleman.control;

import java.time.Instant;

public class Weather {

    private final double temperature;
    private final double precipitation;
    private final int humidity;
    private final int clouds;
    private final double windVelocity;
    private final Location location;
    private final Instant dayHourChecked;
    private final Instant forecastDate;


    public Weather(double temperature, double precipitation, int humidity, int clouds, double windVelocity, Location location, Instant forecastDate) {
        this.temperature = temperature;
        this.precipitation = precipitation;
        this.humidity = humidity;
        this.clouds = clouds;
        this.windVelocity = windVelocity;
        this.location = location;
        this.dayHourChecked = Instant.now();
        this.forecastDate = forecastDate;
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

    public Instant getDateChecked() {
        return dayHourChecked;
    }

    public Instant getForecastDate() {
        return forecastDate;
    }

}

