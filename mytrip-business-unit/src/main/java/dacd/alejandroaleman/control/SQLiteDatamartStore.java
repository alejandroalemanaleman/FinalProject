package dacd.alejandroaleman.control;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class SQLiteDatamartStore implements DatamartStore{

    private Connection connection;

    public SQLiteDatamartStore(String path) {
        try {
            String dbPath = path + "datamart/datamart.db";
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

    private void createPredictionTable(String tableName) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                            "ts TEXT PRIMARY KEY, " +
                            "ss TEXT, " +
                            "prediction_ts TEXT, " +
                            "location_lat TEXT, " +
                            "location_lon TEXT, " +
                            "place TEXT, " +
                            "temperature REAL, " +
                            "precipitation REAL, " +
                            "humidity INTEGER, " +
                            "clouds INTEGER, " +
                            "wind_velocity REAL)"
            );

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPredictionData(JsonObject data) {
        String tableName = "Prediction_" + data.getAsJsonObject("location").get("place").getAsString().replaceAll("\\s", "")
                .replaceAll("-","_");
        System.out.println(tableName);
        createPredictionTable(tableName);
        if (countRowsInTable(tableName) == 5) clearTable(tableName);

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + tableName +
                            "(ts, ss, prediction_ts, location_lat, location_lon, place, " +
                            "temperature, precipitation, humidity, clouds, wind_velocity) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );

            statement.setString(1, data.get("ts").getAsString());
            statement.setString(2, data.get("ss").getAsString());
            statement.setString(3, data.get("prediction_ts").getAsString());
            statement.setString(4, data.getAsJsonObject("location").get("lat").getAsString());
            statement.setString(5, data.getAsJsonObject("location").get("lon").getAsString());
            statement.setString(6, data.getAsJsonObject("location").get("place").getAsString());
            statement.setDouble(7, data.get("temperature").getAsDouble());
            statement.setDouble(8, data.get("precipitation").getAsDouble());
            statement.setInt(9, data.get("humidity").getAsInt());
            statement.setInt(10, data.get("clouds").getAsInt());
            statement.setDouble(11, data.get("windVelocity").getAsDouble());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createHotelTable(String tableName) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                            "ts TEXT PRIMARY KEY, " +
                            "ss TEXT, " +
                            "name TEXT, " +
                            "place TEXT, " +
                            "price TEXT) " //+
                    //"rating TEXT, " +
                    // "wind_velocity REAL)"
            );

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addHotelData(JsonObject data) {
        String tableName = "Hotels_" + data.get("place").getAsString().replaceAll("\\s", "")
                .replaceAll("-","_");
        System.out.println(tableName);
        createHotelTable(tableName);

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + tableName +
                            " (ts, ss, name, place, price) " +
                            "VALUES (?, ?, ?, ?, ?)"
            );

            statement.setString(1, data.get("ts").getAsString());
            statement.setString(2, data.get("ss").getAsString());
            statement.setString(3, data.get("name").getAsString());
            statement.setString(4, data.get("place").getAsString());
            statement.setString(5, data.get("pricePerNight").getAsString());

            statement.execute();
            System.out.println(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save (String data){
        try {
            JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
            String ss = jsonObject.get("ss").getAsString();
            if ("WeatherSupplier".equals(ss)) {
                addPredictionData(jsonObject);
            } else if ("Hotel-Provider".equals(ss)) {
                addHotelData(jsonObject);
            }
            connection.commit(); // Commit the transaction after processing all statements
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback the transaction in case of an exception
            } catch (SQLException rollbackException) {
                throw new RuntimeException("Error rolling back transaction", rollbackException);
            }
            throw new RuntimeException("Error saving data", e);
        }
    }

    public int countRowsInTable(String tableName) {
        int rowCount = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + tableName);

            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }



    private void clearTable(String tableName) {
        // TODO
        try {
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM " + tableName);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*public static void main(String[] args) {
        Main dataHandler = new Main("/Users/alejandroalemanaleman/Downloads/basededatosPRUEBA.db");

        // Ruta del archivo con eventos
        String eventsFilePath = "/Users/alejandroalemanaleman/Downloads/probando/20231228.events";

        // Procesar eventos desde el archivo y agregarlos a la base de datos
        dataHandler.processEventsFromFile(eventsFilePath);

        // Cerrar la conexión
        dataHandler.closeConnection();
    }

     */
}
