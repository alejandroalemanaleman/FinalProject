package dacd.alejandroaleman.model;
import java.util.List;

public class WeatherData {
    private String cod;
    private int message;
    private int cnt;
    private List<Forecast> list;
    private City city;

    public String getCod() {
        return cod;
    }

    public int getMessage() {
        return message;
    }

    public int getCnt() {
        return cnt;
    }

    public List<Forecast> getList() {
        return list;
    }

    public City getCity() {
        return city;
    }
}
