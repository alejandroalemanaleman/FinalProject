package dacd.alejandroaleman.control;

import javax.jms.JMSException;

public class Main {
    public static void main(String[] args) throws JMSException{
        EventReceiver eventReceiver = new EventReceiver();
        eventReceiver.receive();
    }
}