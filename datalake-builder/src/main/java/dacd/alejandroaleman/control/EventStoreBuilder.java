package dacd.alejandroaleman.control;

import dacd.alejandroaleman.control.exceptions.SaveException;

public interface EventStoreBuilder {
    void save(String message, String topic) throws SaveException;
}
