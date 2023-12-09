package dacd.alejandroaleman.control;

import dacd.alejandroaleman.control.exceptions.ReceiverException;

public interface Subscriber {
    void start() throws ReceiverException;
}
