package dacd.alejandroaleman.control.exceptions;
import javax.jms.JMSException;

public class ReceiverException extends JMSException {

    public ReceiverException(String reason, Exception errorCode) {
        super(reason, String.valueOf(errorCode));
    }

    public ReceiverException(String reason) {
        super(reason);
    }
}
