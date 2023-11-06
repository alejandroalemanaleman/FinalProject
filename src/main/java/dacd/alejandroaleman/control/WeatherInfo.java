package dacd.alejandroaleman.control;

import java.time.LocalTime;

public class WeatherInfo {

    private double temperature;
    private double precipitation;
    private int humidity;
    private int clouds;
    private double windVelocity;
    private Location location;
    private final String dayHourChecked;
    private final String forecastDate;


    public WeatherInfo(double temperature, double precipitation, int humidity, int clouds, double windVelocity, Location location, String forecastDate) {
        this.temperature = temperature;
        this.precipitation = precipitation;
        this.humidity = humidity;
        this.clouds = clouds;
        this.windVelocity = windVelocity;
        this.location = location;
        this.dayHourChecked = LocalTime.now().toString().substring(0, 8);
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

    public String getDayHourChecked() {
        return dayHourChecked;
    }

    public String getForecastDate() {
        return forecastDate;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "temperature=" + temperature +
                ", precipitation=" + precipitation +
                ", humidity=" + humidity +
                ", clouds=" + clouds +
                ", windVelocity=" + windVelocity +
                ", location=" + location +
                ", forecastDay='" + forecastDate + '\'' +
                ", dayHourChecked='" + dayHourChecked + '\'' +
                '}';
    }

}

