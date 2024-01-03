package dacd.alejandroaleman.control;

import dacd.alejandroaleman.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class HotelController extends TimerTask {
    private final HotelSupplier tripadvisorHotelSupplier;
    private final HotelStore jmsHotelStore;

    public HotelController(HotelSupplier tripadvisorHotelSupplier, HotelStore jmsHotelStore) {
        this.tripadvisorHotelSupplier = tripadvisorHotelSupplier;
        this.jmsHotelStore = jmsHotelStore;
    }

    public void execute(){
        List<String> places = new ArrayList<>();
        places.add("La-Graciosa");
        places.add("Lanzarote");
        places.add("Fuerteventura");
        places.add("Gran-Canaria");
        places.add("Tenerife");
        places.add("La-Gomera");
        places.add("La-Palma");
        places.add("El-Hierro");

        for (String place : places) {
            List<Hotel> hotels = tripadvisorHotelSupplier.get(place);
            jmsHotelStore.save(hotels);
        }
        System.out.println("-----[MESSAGE]: ALL DATA SENT.-----");
    }

    public void run(){
        execute();
    }

}
