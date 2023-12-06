package dacd.alejandroaleman.control.exceptions;

import javax.jms.JMSException;

public class StoreException extends JMSException {

    public StoreException(String reason, String errorCode) {
        super(reason, errorCode);
    }

    public StoreException(String reason) {
        super(reason);
    }
}
