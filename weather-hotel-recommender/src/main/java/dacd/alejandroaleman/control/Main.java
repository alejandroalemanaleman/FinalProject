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
            DatamartController datamartController = new DatamartController(new TopicSubscriber(new SQLiteDatamartStore(args[0]), "prediction.Weather", "information.Hotel", semaphore));
            datamartController.excute();

        } catch (ReceiverException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(100000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            new SwingGUIController(new DatamartProvider(args[0], List.of("La Graciosa", "Lanzarote", "Fuerteventura", "Gran Canaria", "Tenerife", "La Gomera", "La Palma", "El Hierro"))).execute();
        }
    }
}