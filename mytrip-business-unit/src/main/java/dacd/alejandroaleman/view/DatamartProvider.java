package dacd.alejandroaleman.view;

import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
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
    private String dbPath;
    private Connection connection;

    public DatamartProvider(String path) {
        try {
            dbPath = path + "datamart/datamart.db";
            new File(dbPath).getParentFile().mkdirs();
            this.connection = connect(dbPath);
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection connect(String dbPath) {
        try {
            String url = "jdbc:sqlite:" + dbPath;
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, List<Weather>> getPredictionFromPlaces(List<String> places) {
        Map<String, List<Weather>> predictionsFromPlaces = new HashMap<>();
        for (String place : places){
            List<Weather> weathers = new ArrayList<>();
            String tableName = "Prediction_" + place.replaceAll(" ", "_"); //TODO DEBERIA SER PREDICTIONS EN TODO
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + tableName);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Instant prediction_ts = Instant.parse(resultSet.getString("prediction_ts"));
                    Double temperature = resultSet.getDouble("temperature");
                    Double precipitation = resultSet.getDouble("precipitation");
                    int humidity = resultSet.getInt("humidity");
                    int clouds = resultSet.getInt("clouds");
                    int wind_velocity = resultSet.getInt("wind_velocity");
                    weathers.add(new Weather(prediction_ts, place, temperature, precipitation, humidity, clouds, wind_velocity));
                }
                predictionsFromPlaces.put(place, weathers);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return predictionsFromPlaces;
    }

    public Map<String, List<Hotel>> getHotelsFromPlaces(List<String> places) {
        Map<String, List<Hotel>> hotelsFromPlaces = new HashMap<>();
        for (String place : places){
            List<Hotel> hotels = new ArrayList<>();
            String tableName = "Hotels_" + place.replaceAll(" ", "_");
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + tableName);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String priceRange = resultSet.getString("price");
                    hotels.add(new Hotel(name, place, priceRange));
                }
                hotelsFromPlaces.put(place, hotels);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return hotelsFromPlaces;
    }

    public static void main(String[] args) {
        System.out.println(args[0]);
        DatamartProvider datamartProvider = new DatamartProvider(args[0]);
        List<String> places = new ArrayList<>();
        places.add("La Graciosa");
        places.add("Lanzarote");
        places.add("Fuerteventura");
        places.add("Gran Canaria");
        places.add("Tenerife");
        places.add("La Gomera");
        places.add("La Palma");
        places.add("El Hierro");
        datamartProvider.getHotelsFromPlaces(places);
        datamartProvider.getPredictionFromPlaces(places);
    }
}
