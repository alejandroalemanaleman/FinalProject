package dacd.alejandroaleman.control;

import dacd.alejandroaleman.model.Hotel;

import java.util.List;

public interface HotelStore {
    void save(List<Hotel> hotels);
}
