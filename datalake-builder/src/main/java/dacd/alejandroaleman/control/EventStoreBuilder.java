package dacd.alejandroaleman.control;

import dacd.alejandroaleman.control.exceptions.ReceiverException;
import dacd.alejandroaleman.control.exceptions.SaveException;

import java.io.IOException;

public interface EventStoreBuilder {
    void save(String message) throws SaveException;
}
