package dacd.alejandroaleman.control;

import java.time.LocalTime;

public class WeatherInfo {

    private double temperature;
    private double precipitation;
    private double humidity;
    private double clouds;
    private double windVelocity;
    private Location location;
    private LocalTime hourChecked;
    private LocalTime hour;
    private String city;

    public WeatherInfo(double temperature, double precipitation, double humidity, double clouds, double windVelocity, Location location) {
        this.temperature = temperature;
        this.precipitation = precipitation;
        this.humidity = humidity;
        this.clouds = clouds;
        this.windVelocity = windVelocity;
        this.location = location;
        this.hourChecked = LocalTime.now();
        this.hour = LocalTime.of(12,00);
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getClouds() {
        return clouds;
    }

    public double getWindVelocity() {
        return windVelocity;
    }

    public Location getLocation() {
        return location;
    }

    public LocalTime getHourChecked() {
        return hourChecked;
    }

    public LocalTime getHour() {
        return hour;
    }
}

