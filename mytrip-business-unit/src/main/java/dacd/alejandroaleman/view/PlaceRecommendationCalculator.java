package dacd.alejandroaleman.view;

import dacd.alejandroaleman.model.Weather;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceRecommendationCalculator {
    private Map<String, List<Weather>> weatherMap;

    public PlaceRecommendationCalculator(Map<String, List<Weather>> weatherMap) {
        this.weatherMap = weatherMap;
    }

    public String calculateRecommendationScore() {
        String bestIsland = null;
        double bestTemperature = Double.MIN_VALUE;
        double lowestPrecipitation = Double.MAX_VALUE;

        for (Map.Entry<String, List<Weather>> entry : weatherMap.entrySet()) {
            String island = entry.getKey();
            List<Weather> weatherList = entry.getValue();

            // Calcular la temperatura promedio y la probabilidad de precipitación promedio para la isla
            double averageTemperature = calculateAverageTemperature(weatherList);
            double averagePrecipitation = calculateAveragePrecipitation(weatherList);

            // Actualizar la mejor isla si encontramos una con mejores condiciones
            if (averageTemperature > bestTemperature ||
                    (averageTemperature == bestTemperature && averagePrecipitation < lowestPrecipitation)) {
                bestIsland = island;
                bestTemperature = averageTemperature;
                lowestPrecipitation = averagePrecipitation;
            }
        }

        return bestIsland;
    }

    private static double calculateAverageTemperature(List<Weather> weatherList) {
        return weatherList.stream()
                .mapToDouble(Weather::getTemperature)
                .average()
                .orElse(0.0);
    }

    private static double calculateAveragePrecipitation(List<Weather> weatherList) {
        return weatherList.stream()
                .mapToDouble(Weather::getPrecipitation)
                .average()
                .orElse(0.0);
    }

}
