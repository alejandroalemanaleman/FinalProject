package dacd.alejandroaleman.control;

public class Main {
    public static void main(String[] args) {
        HotelController hotelController = new HotelController(new TripadvisorHotelSupplier(args[0]), new JMSHotelStore());
        hotelController.save();
    }
}