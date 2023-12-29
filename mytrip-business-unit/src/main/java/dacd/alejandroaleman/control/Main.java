package dacd.alejandroaleman.control;

import dacd.alejandroaleman.control.exceptions.ReceiverException;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);

        try {
            FileEventStoreBuilder builder1 = new FileEventStoreBuilder(args[0]);
            DatamartController datamartController = new DatamartController(new TopicSubscriber(new SQLiteDatamartStore(args[0]), "prediction.Weather", "prueba.Hotel", semaphore));
            datamartController.excute();

        } catch (ReceiverException e) {
            throw new RuntimeException(e);
        }

    }
}