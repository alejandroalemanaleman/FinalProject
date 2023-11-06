package dacd.alejandroaleman.control;

import java.util.List;

public class Main {

    public static List<List<WeatherInfo>> loadWeatherData(){
        WeatherController weatherController = new WeatherController();
        return weatherController.execute();
    }


    public static void saveWeatherData(WeatherInfo weatherInfo){
        SQLiteWeatherStore sqLiteWeatherStore = new SQLiteWeatherStore(weatherInfo);
    }


    public static void main(String[] args) {

        List<List<WeatherInfo>>  weatherInfoList = loadWeatherData();
        //saveWeatherData(weatherInfoList.get(0));



    }

}
