package dacd.alejandroaleman.view;

import dacd.alejandroaleman.model.Weather;

import java.util.List;
import java.util.Map;

public class PlaceRecommendationCalculator {
    private Map<String, List<Weather>> weatherMap;

    public PlaceRecommendationCalculator(Map<String, List<Weather>> weatherMap) {
        this.weatherMap = weatherMap;
    }

    public String calculateRecommendationScore() {
        String bestIsland = null;
        double bestScore = Double.MIN_VALUE;

        for (Map.Entry<String, List<Weather>> entry : weatherMap.entrySet()) {
            String island = entry.getKey();
            List<Weather> weatherList = entry.getValue();

            // Calcular el puntaje para la isla
            double score = calculateScore(weatherList);

            // Actualizar la mejor isla si encontramos una con un mejor puntaje
            if (score > bestScore) {
                bestIsland = island;
                bestScore = score;
            }
        }

        return bestIsland;
    }

    private double calculateScore(List<Weather> weatherList) {
        // Puedes ajustar estos pesos según la importancia de cada parámetro
        double temperatureWeight = 0.4;
        double precipitationWeight = 0.3;
        double humidityWeight = 0.1;
        double cloudsWeight = 0.1;
        double windVelocityWeight = 0.1;

        // Calcular el puntaje promedio ponderado
        double averageScore = weatherList.stream()
                .mapToDouble(weather ->
                        temperatureWeight * weather.getTemperature() +
                                precipitationWeight * weather.getPrecipitation() +
                                humidityWeight * weather.getHumidity() +
                                cloudsWeight * weather.getClouds() +
                                windVelocityWeight * weather.getWindVelocity())
                .average()
                .orElse(0.0);

        return averageScore;
    }
}
