package dacd.alejandroaleman.control;

import dacd.alejandroaleman.control.exceptions.ReceiverException;

public class Main {
    public static void main(String[] args) throws ReceiverException {
        TopicSubscriber predictionWeatherEventReceiver = new TopicSubscriber(new FileEventStoreBuilder(args[0]), "prediction.Weather");
        predictionWeatherEventReceiver.start();
        /*TopicSubscriber eventReceiver = new TopicSubscriber(new FileEventStoreBuilder(args[0]), "businessUnitTopic");
        eventReceiver.start();*/

        //TODO hacer que reciba como args dos topics y en el main se cree dos TopicSubs. y le pases a cada uno un topic(cambiar Topic subs y FileEvent...)
    }
}