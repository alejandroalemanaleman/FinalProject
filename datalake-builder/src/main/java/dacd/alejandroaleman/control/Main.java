package dacd.alejandroaleman.control;

import dacd.alejandroaleman.control.exceptions.ReceiverException;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        // Crear un semáforo con un permiso
        Semaphore semaphore = new Semaphore(1);

        try {
            FileEventStoreBuilder builder1 = new FileEventStoreBuilder(args[0]);
            FileEventStoreBuilder builder2 = new FileEventStoreBuilder(args[0]);

            TopicSubscriber datalakeBuilder = new TopicSubscriber(builder1, "prediction.Weather", "prueba.Hotel", semaphore);
            datalakeBuilder.start();

            // Iniciar el segundo TopicSubscriber
        } catch (ReceiverException e) {
            e.printStackTrace();
        }
    }
}
