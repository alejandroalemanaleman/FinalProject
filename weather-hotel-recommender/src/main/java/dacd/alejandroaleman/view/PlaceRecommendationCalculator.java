package dacd.alejandroaleman.view;

import dacd.alejandroaleman.model.Weather;

import java.util.List;
import java.util.Map;

public class PlaceRecommendationCalculator {

    public PlaceRecommendationCalculator() {
    }

    public String calculateRecommendation(Map<String, List<Weather>> weatherMap) {
        String bestPlace = null;
        double bestScore = Double.MIN_VALUE;

        for (Map.Entry<String, List<Weather>> entry : weatherMap.entrySet()) {
            String place = entry.getKey();
            List<Weather> weatherList = entry.getValue();
            double score = calculateScore(weatherList);
            if (score > bestScore) {
                bestPlace = place;
                bestScore = score;
            }
        }
        return bestPlace;
    }

    private double calculateScore(List<Weather> weatherList) {
        double temperatureWeight = 0.4;
        double precipitationWeight = 0.3;
        double humidityWeight = 0.1;
        double cloudsWeight = 0.1;
        double windVelocityWeight = 0.1;

        return weatherList.stream()
                .mapToDouble(weather ->
                        temperatureWeight * weather.getTemperature() +
                                precipitationWeight * weather.getPrecipitation() +
                                humidityWeight * weather.getHumidity() +
                                cloudsWeight * weather.getClouds() +
                                windVelocityWeight * weather.getWindVelocity())
                .average()
                .orElse(0.0);
    }
}
