package dacd.alejandroaleman.control;

import dacd.alejandroaleman.control.exceptions.ReceiverException;
import dacd.alejandroaleman.control.exceptions.SaveException;

import javax.jms.JMSException;

public class Main {
    public static void main(String[] args) throws SaveException, ReceiverException {
        TopicSubscriber eventReceiver = new TopicSubscriber(new FileEventStoreBuilder(args[0]));
        eventReceiver.start();
    }
}