package dacd.alejandroaleman.control;

import dacd.alejandroaleman.control.exceptions.ReceiverException;
import dacd.alejandroaleman.view.DatamartProvider;
import dacd.alejandroaleman.view.SwingGUIController;

import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        try {
            TopicSubscriber topicSubscriber = new TopicSubscriber(new SQLiteDatamartStore(args[0]), "prediction.Weather", "information.Hotel", semaphore);
            topicSubscriber.start();
            Thread.sleep(60000);

        } catch (ReceiverException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            new SwingGUIController(new DatamartProvider(args[0], List.of("La Graciosa", "Lanzarote", "Fuerteventura", "Gran Canaria", "Tenerife", "La Gomera", "La Palma", "El Hierro"))).execute();
        }
    }
}