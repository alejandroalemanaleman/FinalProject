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
        try {
            Thread.sleep(120000); // 2 minutes in milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Now, show the interface or perform any other initialization steps
        System.out.println("INTERFAZ READY TO SLAY");

    }
}