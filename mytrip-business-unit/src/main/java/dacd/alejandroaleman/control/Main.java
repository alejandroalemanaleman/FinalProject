package dacd.alejandroaleman.control;

import dacd.alejandroaleman.control.exceptions.ReceiverException;
import dacd.alejandroaleman.view.GUIInterface;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);

        try {
            DatamartController datamartController = new DatamartController(new TopicSubscriber(new SQLiteDatamartStore(args[0]), "prediction.Weather", "prueba.Hotel", semaphore));
            datamartController.excute();

        } catch (ReceiverException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(100000); // 1,8 minutes in milliseconds creo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            new GUIInterface().execute();

        }



    }
}