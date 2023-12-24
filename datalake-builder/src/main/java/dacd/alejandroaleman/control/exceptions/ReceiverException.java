package dacd.alejandroaleman.control.exceptions;
import javax.jms.JMSException;

public class ReceiverException extends JMSException {

    public ReceiverException(String reason, String errorCode) {
        super(reason, errorCode);
    }

    public ReceiverException(String reason) {
        super(reason);
    }
}
