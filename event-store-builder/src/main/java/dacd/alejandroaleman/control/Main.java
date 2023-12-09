package dacd.alejandroaleman.control;

import dacd.alejandroaleman.control.exceptions.ReceiverException;

public class Main {
    public static void main(String[] args) throws ReceiverException {
        TopicSubscriber eventReceiver = new TopicSubscriber(new FileEventStoreBuilder(args[0]));
        eventReceiver.start();
    }
}