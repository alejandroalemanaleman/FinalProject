package dacd.alejandroaleman.control;

import dacd.alejandroaleman.model.*;
import java.util.ArrayList;
import java.util.List;

public class HotelController {
    private final HotelSupplier tripadvisorHotelSupplier;

    public HotelController(HotelSupplier tripadvisorHotelSupplier) {
        this.tripadvisorHotelSupplier = tripadvisorHotelSupplier;
    }

    public void save(){
        List<Location> locationList = new ArrayList<>();
        locationList.add(new Location("29.2298950", "-13.5050417", "La Graciosa"));
        locationList.add(new Location("28.9611348", "-13.5512381", "Lanzarote"));
        locationList.add(new Location("28.5010371", "-13.8628859", "Fuerteventura"));
        locationList.add(new Location("28.12281998218409", "-15.427139106449038", "Gran Canaria"));
        locationList.add(new Location("28.466579957829115", "-16.249983979646377", "Tenerife"));
        locationList.add(new Location("28.0914976", "-17.1107147", "La Gomera"));
        locationList.add(new Location("28.6837586", "-17.7645926", "La Palma"));
        locationList.add(new Location("27.810376412061633", "-17.91380238618073", "El Hierro"));

        for (Location location : locationList) {
            //List<Hotel> hotels = tripadvisorHotelSupplier.get(location);
            tripadvisorHotelSupplier.get(location);
            //jmsWeatherStore.save(hotels);
        }
    }

}