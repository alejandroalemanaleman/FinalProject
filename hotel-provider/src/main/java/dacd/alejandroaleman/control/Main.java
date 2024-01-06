package dacd.alejandroaleman.control;

import java.util.Timer;

public class Main {
    public static void main(String[] args) {
        Timer timer = new Timer();
        long delay = 0;
        long interval = 10 * 60 * 60 * 1000;
        HotelController hotelController = new HotelController(new TripadvisorHotelSupplier(), new JMSHotelStore());
        timer.schedule(hotelController, delay, interval);
    }
}