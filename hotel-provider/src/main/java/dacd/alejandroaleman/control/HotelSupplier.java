package dacd.alejandroaleman.control;

import dacd.alejandroaleman.model.Hotel;
import dacd.alejandroaleman.model.Location;

import java.util.List;

public interface HotelSupplier {
    List<Hotel> get(String place);
}
