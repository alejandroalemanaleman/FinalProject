package dacd.alejandroaleman.control;

import dacd.alejandroaleman.model.Weather;
import dacd.alejandroaleman.model.WeatherStore;

import java.io.File;
import java.sql.*;
import java.util.List;

public class SQLiteWeatherStore implements WeatherStore {
    private final String dbPath;

    public SQLiteWeatherStore(String dbPath) {
        this.dbPath = dbPath;
        new File(this.dbPath).getParentFile().mkdirs();
    }

    public void save(List<List<Weather>> listOfAllWeather) {

        for (int i = 0; i < listOfAllWeather.size(); i++) {
            for (int j = 0; j < listOfAllWeather.get(i).size(); j++) {
                saveInstance(listOfAllWeather.get(i).get(j));
            }
        }
    }

    private void saveInstance(Weather weather) {
        try (Connection connection = connect(this.dbPath)) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            createTable(statement, weather);
            if (dataExists(connection, weather)) {
                update(connection, weather);
            } else {
                insert(connection, weather);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createTable(Statement statement, Weather weather) throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS " + weather.getLocation().getIsland() + " (" +
                "id INTEGER PRIMARY KEY,\n" +
                "city TEXT NOT NULL,\n" +
                "hourSaved TEXT NOT NULL,\n" +
                "temperature REAL,\n" +
                "precipitation REAL,\n" +
                "humidity INTEGER,\n" +
                "clouds INTEGER,\n" +
                "windvelocity REAL,\n" +
                "forecastDay TEXT NOT NULL" +
                ");");
    }

    private static void insert(Connection connection, Weather weather) throws SQLException {
        String tableName = weather.getLocation().getIsland();
        String insertQuery = "INSERT INTO " + tableName + " (city, hourSaved, temperature, precipitation, humidity, clouds, windvelocity, forecastDay) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, weather.getLocation().getCity());
            preparedStatement.setString(2, weather.getDateChecked().toString());
            preparedStatement.setDouble(3, weather.getTemperature());
            preparedStatement.setDouble(4, weather.getPrecipitation());
            preparedStatement.setDouble(5, weather.getHumidity());
            preparedStatement.setDouble(6, weather.getClouds());
            preparedStatement.setDouble(7, weather.getWindVelocity());
            preparedStatement.setString(8, weather.getForecastDate().toString());

            preparedStatement.executeUpdate();
            System.out.println("Data from " + weather.getLocation().getIsland() +" stored correctly at: " + weather.getDateChecked());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void update(Connection connection, Weather weather) throws SQLException {
        String tableName = weather.getLocation().getIsland();
        String updateQuery = "UPDATE " + tableName +
                " SET city = ?, hourSaved = ?, temperature = ?, precipitation = ?, humidity = ?, clouds = ?, windvelocity = ?" +
                " WHERE forecastDay = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, weather.getLocation().getCity());
            preparedStatement.setString(2, weather.getDateChecked().toString());
            preparedStatement.setDouble(3, weather.getTemperature());
            preparedStatement.setDouble(4, weather.getPrecipitation());
            preparedStatement.setDouble(5, weather.getHumidity());
            preparedStatement.setDouble(6, weather.getClouds());
            preparedStatement.setDouble(7, weather.getWindVelocity());
            preparedStatement.setString(8, weather.getForecastDate().toString());

            preparedStatement.executeUpdate();
            System.out.println("Data from " + weather.getLocation().getIsland() +" updated correctly at: " + weather.getDateChecked());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection connect(String dbPath) {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:" + dbPath;
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private static boolean dataExists(Connection connection, Weather weather) {
        String tableName = weather.getLocation().getIsland();
        String forecastDay = weather.getForecastDate().toString();
        String query = "SELECT 1 FROM " + tableName + " WHERE forecastDay = ? LIMIT 1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, forecastDay);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}