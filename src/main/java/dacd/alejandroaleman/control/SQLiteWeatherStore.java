package dacd.alejandroaleman.control;
import java.io.File;
import java.sql.*;

public class SQLiteWeatherStore {
    public SQLiteWeatherStore(WeatherInfo weatherInfo) {
        String dbPath = "jdbc/WeatherDatabase.db"; // Reemplaza con la ubicación de tu base de datos
        new File(dbPath).getParentFile().mkdirs();

        try (Connection connection = connect(dbPath)) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            createTable(statement, weatherInfo);

            if (dataCount(connection, weatherInfo.getLocation().getPlace()) >= 5) {
                // Si hay 5 registros, elimina el registro más antiguo
                deleteOldestRecord(connection, weatherInfo.getLocation().getPlace());
            }

            insert(connection, weatherInfo);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void delete(Statement statement, WeatherInfo weatherInfo) throws SQLException {
        statement.execute("DELETE FROM " + weatherInfo.getLocation().getPlace());
    }
// CAMBIOS NUEVOS AQUI
private int dataCount(Connection connection, String tableName) throws SQLException {
    String countQuery = "SELECT COUNT(*) FROM " + tableName;
    try (PreparedStatement preparedStatement = connection.prepareStatement(countQuery);
         ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
    }
    return 0;
}

    private void deleteOldestRecord(Connection connection, String tableName) throws SQLException {
        String deleteQuery = "DELETE FROM " + tableName + " WHERE id IN (SELECT id FROM " + tableName + " ORDER BY id ASC LIMIT 1)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(deleteQuery);
        }
    }

    private void deleteAll(Statement statement, String tableName) throws SQLException {
        statement.execute("DELETE FROM " + tableName);
    }
//HASTA AQUI
    private static void createTable(Statement statement, WeatherInfo weatherInfo) throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS " + weatherInfo.getLocation().getPlace() + " (" +
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

    private static void insert(Connection connection, WeatherInfo weatherInfo) throws SQLException {
        String tableName = weatherInfo.getLocation().getPlace();
        String insertQuery = "INSERT INTO " + tableName + " (city, hourSaved, temperature, precipitation, humidity, clouds, windvelocity, forecastDay) " +
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
