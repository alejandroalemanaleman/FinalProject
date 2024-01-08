package dacd.alejandroaleman.model;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class Weather {
    private final Instant predictionTs;
    private final String place;
    private final double temperature;
    private final double precipitation;
    private final int humidity;
    private final int clouds;
    private final double windVelocity;


    public Weather(Instant predictionTs, String place, Double temperature, double precipitation, int humidity, int clouds, double windVelocity) {
        this.predictionTs = predictionTs;
        this.place = place;
        this.temperature = temperature;
        this.precipitation = precipitation;
        this.humidity = humidity;
        this.clouds = clouds;
        this.windVelocity = windVelocity;
    }

    public Instant getPredictionTs() {
        return predictionTs;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(Date.from(predictionTs));

        return String.format("Day: %-12s  Temperature: %-5.2f  Precipitation: %-5.2f%%  Humidity: %-3d  Clouds: %-3d  Wind Velocity: %-5.2f",
                formattedDate, temperature, precipitation * 100, humidity, clouds, windVelocity);
    }




}
