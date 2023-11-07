package dacd.alejandroaleman.control;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends TimerTask{

    public static List<List<WeatherInfo>> loadWeatherData(){
        WeatherController weatherController = new WeatherController();
        return weatherController.execute();
    }


    public static void saveWeatherData(List<List<WeatherInfo>> weatherInfoList){

        for (int i = 0; i < weatherInfoList.size(); i++) {
            for (int j = 0; j < weatherInfoList.get(i).size(); j++){
                SQLiteWeatherStore sqLiteWeatherStore = new SQLiteWeatherStore(weatherInfoList.get(i).get(j));
            }
        }
    }
//TODO change the way data is updated.

    public static void main(String[] args) {
        Timer timer = new Timer();
        long delay = 0; // Iniciar de inmediato
        //long period = 6 * 60 * 60 * 1000; // 6 horas en milisegundos
        long period = 60000 * 60 ; // cada hora
        timer.schedule(new Main(), delay, period);
    }

    @Override
    public void run() {
        System.out.println("hola este soy yo antes de load");
        List<List<WeatherInfo>>  weatherInfoList = loadWeatherData();
        System.out.println("hola este soy yo despues de load y  antes de save");
        saveWeatherData(weatherInfoList);
        System.out.println("hola este soy yo despues de save ^^");
    }
}
