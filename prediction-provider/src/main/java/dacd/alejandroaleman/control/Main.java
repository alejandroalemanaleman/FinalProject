package dacd.alejandroaleman.control;

import java.util.Timer;

public class Main {
    public static void main(String[] args) {
/*
        Timer timer = new Timer();
        long delay = 0;
        long interval = 6 * 60 * 60 * 1000;


 */
        WeatherController weatherController = new WeatherController(new OpenWeatherMapSupplier(args[0]), new JMSWeatherStore());
        //timer.schedule(weatherController, delay, interval);
    }
}