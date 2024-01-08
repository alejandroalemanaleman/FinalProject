package dacd.alejandroaleman.control;

import dacd.alejandroaleman.model.Hotel;

import java.util.List;

public interface HotelSupplier {
    List<Hotel> get(String place);
}
