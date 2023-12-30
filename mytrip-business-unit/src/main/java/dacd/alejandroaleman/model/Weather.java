package dacd.alejandroaleman.model;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class Weather {
    private final Instant prediction_ts;
    private final String place;
    private final double temperature;
    private final double precipitation;
    private final int humidity;
    private final int clouds;
    private final double windVelocity;


    public Weather(Instant prediction_ts, String place, Double temperature, double precipitation, int humidity, int clouds, double windVelocity) {
        this.prediction_ts = prediction_ts;
        this.place = place;
        this.temperature = temperature;
        this.precipitation = precipitation;
        this.humidity = humidity;
        this.clouds = clouds;
        this.windVelocity = windVelocity;
    }

    public Instant getPredictionTime() {
        return prediction_ts;
    }

    public String getPlace() {
        return place;
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

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(Date.from(prediction_ts));

        return "Prediction Time: " + formattedDate +
                ", Place: " + place +
                ", Temperature: " + temperature +
                ", Precipitation: " + precipitation +
                ", Humidity: " + humidity +
                ", Clouds: " + clouds +
                ", Wind Velocity: " + windVelocity;
    }
}
