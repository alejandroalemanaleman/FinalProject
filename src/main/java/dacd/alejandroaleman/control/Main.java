package dacd.alejandroaleman.control;

import java.io.File;
import java.util.Timer;

public class Main {
    public static void main(String[] args) {
        Timer timer = new Timer();
        long delay = 0;  // Retardo inicial antes de la primera ejecución (0 segundos)
        long interval = 6 * 60 * 60 * 1000;

        File fileWithApiKey = new File("src/main/resources/APIKEY.txt");
        File fileWithDbPath = new File("src/main/resources/databasePath.txt");
        WeatherController weatherController = new WeatherController(new OpenWeatherMapSupplier(fileWithApiKey), new SQLiteWeatherStore(fileWithDbPath));

        timer.schedule(weatherController, delay, interval);

        /*
        String workingDirectory = System.getProperty("user.dir");
        System.out.println("Current Working Directory: " + workingDirectory);
         */

    }
}