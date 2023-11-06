package dacd.alejandroaleman.control;
import java.io.File;
import java.sql.*;

public class SQLiteWeatherStore {
    public SQLiteWeatherStore(WeatherInfo weatherInfo) {
        String dbPath = "jdbc/WeatherDatabase.db";
        new File(dbPath).getParentFile().mkdirs();
        try(Connection connection = connect(dbPath)) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            createTable(statement, weatherInfo);
            insert(connection, weatherInfo);
            /*
            update(statement);
             */
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void delete(Statement statement, WeatherInfo weatherInfo) throws SQLException {
        statement.execute("DELETE FROM " + weatherInfo.getLocation().getPlace());
    }

    private static void createTable(Statement statement, WeatherInfo weatherInfo) throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS " + weatherInfo.getLocation().getPlace() + " (" +
                "id INTEGER PRIMARY KEY,\n" +
                "city TEXT NOT NULL,\n" +
                "hour TEXT NOT NULL,\n" +
                "temperature REAL,\n" +
                "precipitation REAL,\n" +
                "humidity REAL,\n" +
                "clouds REAL,\n" +
                "windvelocity REAL,\n" +
                "hourchecked TEXT NOT NULL" +
                ");");
    }

    private static void insert(Connection connection, WeatherInfo weatherInfo) throws SQLException {
        String tableName = weatherInfo.getLocation().getPlace();
        String insertQuery = "INSERT INTO " + tableName + " (city, hour, temperature, precipitation, humidity, clouds, windvelocity, hourchecked) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, weatherInfo.getLocation().getCity());
            preparedStatement.setString(2, weatherInfo.getDayHourChecked());
            preparedStatement.setDouble(3, weatherInfo.getTemperature());
            preparedStatement.setDouble(4, weatherInfo.getPrecipitation());
            preparedStatement.setDouble(5, weatherInfo.getHumidity());
            preparedStatement.setDouble(6, weatherInfo.getClouds());
            preparedStatement.setDouble(7, weatherInfo.getWindVelocity());
            preparedStatement.setString(8, weatherInfo.getForecastDate());

            preparedStatement.executeUpdate();
            System.out.println("Datos insertados correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(Statement statement) throws SQLException {
        statement.execute("UPDATE products\n" +
                "SET name = 'orbea500' \n" +
                "WHERE" + " name='orbea';");
        System.out.println("Table products updated");
    }

    public static Connection connect(String dbPath) {
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

}
