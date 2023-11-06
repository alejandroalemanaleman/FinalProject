package dacd.alejandroaleman.control;

import java.util.List;

public class Main {

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


    public static void main(String[] args) {

        List<List<WeatherInfo>>  weatherInfoList = loadWeatherData();
        System.out.println(weatherInfoList.get(3).get(0).getLocation().getCity() + "   HOLA");
        saveWeatherData(weatherInfoList);



    }

}
