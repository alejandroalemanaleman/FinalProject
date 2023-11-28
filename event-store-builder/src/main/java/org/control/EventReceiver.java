package org.control;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;

public class EventReceiver {
    //private static String clientId = "clienteDuradero"; // ID de cliente
    //private static String subscriptionName = "suscripcionDuradera"; // Nombre de suscripción duradera

    public void receive() throws JMSException {
        MessageConsumer consumer = getMessageConsumer();

        consumer.setMessageListener(message -> {
            try {
                if (message instanceof TextMessage) {
                    System.out.println("Mensaje recibido: " + ((TextMessage) message).getText());
                    saveEvent(((TextMessage) message).getText());

                } else {
                    System.out.println("Tipo de mensaje no compatible: " + message.getClass().getName());
                }
            } catch (JMSException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("Esperando mensajes...");
    }

    private MessageConsumer getMessageConsumer() throws JMSException {
        String url = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        String subject = "prueba4";
        Destination destination = session.createQueue(subject);

        return session.createConsumer(destination);
    }

    private void saveEvent(String jsonString) throws IOException {
        Gson gson = new Gson();
        JsonObject event = gson.fromJson(jsonString, JsonObject.class);
        String ss = event.get("ss").getAsString();
        String ts = convertDate(event.get("ts").getAsString());
        String directoryPath = "eventstore/prediction.Weather/" + ss ;
        String filePath = directoryPath + "/" + ts + ".events";

        createDirectoryIfNotExists(directoryPath);
        // Guardar el contenido del evento en el archivo
        saveToFile(filePath, jsonString);
    }

    private void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private void saveToFile(String filePath, String content) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(content);
        }
    }
    private String convertDate(String inputString) {
            Instant instant = Instant.parse(inputString);
            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, java.time.ZoneOffset.UTC);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            return zonedDateTime.format(outputFormatter);
    }
}