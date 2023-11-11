package dacd.alejandroaleman.control;

import java.io.File;
import java.util.Timer;

public class Main {
    public static void main(String[] args) {
        String apikey = args[0];
        String dbPath = args[1];
        Timer timer = new Timer();
        long delay = 0;  // Retardo inicial antes de la primera ejecución (0 segundos)
        //long interval = 6 * 60 * 60 * 1000;
        long interval = 10 * 60 * 1000;

        File fileWithApiKey = new File("src/main/resources/APIKEY.txt");
        File fileWithDbPath = new File("src/main/resources/databasePath.txt");
        WeatherController weatherController = new WeatherController(new OpenWeatherMapSupplier(apikey), new SQLiteWeatherStore(dbPath));

        timer.schedule(weatherController, delay, interval);


    }
}