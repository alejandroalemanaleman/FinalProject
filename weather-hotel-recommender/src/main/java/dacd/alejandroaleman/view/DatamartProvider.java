package dacd.alejandroaleman.view;

import dacd.alejandroaleman.model.Hotel;
import dacd.alejandroaleman.model.Weather;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatamartProvider {
    private final String dbPath;
    private final Connection connection;
    private final List<String> places;

    public DatamartProvider(String path, List<String> places) {
        try {
            this.places = places;
            dbPath = path + "/datamart/datamart.db";
            new File(dbPath).getParentFile().mkdirs();
            this.connection = connect(dbPath);
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection connect(String dbPath) {
        try {
            String url = "jdbc:sqlite:" + dbPath;
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, List<Weather>> getPredictions() {
        Map<String, List<Weather>> predictionsFromPlaces = new HashMap<>();
        for (String place : places){
            List<Weather> weathers = new ArrayList<>();
            String tableName = "Prediction_" + place.replaceAll(" ", "_");
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + tableName);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Instant predictionTs = Instant.parse(resultSet.getString("predictionTs"));
                    Double temperature = resultSet.getDouble("temperature");
                    Double precipitation = resultSet.getDouble("precipitation");
                    int humidity = resultSet.getInt("humidity");
                    int clouds = resultSet.getInt("clouds");
                    int wind_velocity = resultSet.getInt("wind_velocity");
                    weathers.add(new Weather(predictionTs, place, temperature, precipitation, humidity, clouds, wind_velocity));
                }
                predictionsFromPlaces.put(place, weathers);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return predictionsFromPlaces;
    }

    public Map<String, List<Hotel>> getHotels() {
        Map<String, List<Hotel>> hotelsFromPlaces = new HashMap<>();
        for (String place : places){
            List<Hotel> hotels = new ArrayList<>();
            String tableName = "Hotels_" + place.replaceAll(" ", "_");
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + tableName);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String priceRangePerNight = resultSet.getString("priceRangePerNight");
                    String rating = resultSet.getString("rating");
                    hotels.add(new Hotel(name, place, priceRangePerNight, rating));
                }
                hotelsFromPlaces.put(place, hotels);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return hotelsFromPlaces;
    }
}
