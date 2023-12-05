package dacd.alejandroaleman.control;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
import java.io.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class EventReceiver {
    //private static String clientId = "clienteDuradero"; // ID de cliente
    //private static String subscriptionName = "suscripcionDuradera"; // Nombre de suscripción duradera
    private final String brokerUrl = "tcp://localhost:61616";
    private final String topicName = "prueba.Weather2";

    private final String userPath;

    public EventReceiver(String userPath) {
        this.userPath = userPath;
    }


    public void receive() throws JMSException {
        ConnectionFactory connFactory = new ActiveMQConnectionFactory(brokerUrl);
        Connection connection = connFactory.createConnection();
        connection.setClientID("Ale");
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(topicName);

        MessageConsumer consumer = session.createDurableSubscriber(topic, "Ale");
        consumer.setMessageListener(message -> {
            try {
                String text = ((TextMessage) message).getText();
                store(text);
                System.out.println("Received message: " + text);
            } catch (JMSException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void store(String jsonString) throws IOException {
        Gson gson = new Gson();
        JsonObject event = gson.fromJson(jsonString, JsonObject.class);
        String ss = event.get("ss").getAsString();
        String ts = convertDate(event.get("ts").getAsString());
        System.out.println("ESTE ES EL USER PATH:  " + userPath);
        String directoryPath = userPath + "/eventstore/prediction.Weather/" + ss ;
        String filePath = directoryPath + "/" + ts + ".events";
        storeEventInFile(directoryPath, filePath, jsonString);
        System.out.println("[Event: ]" + jsonString + "[Stored at:] " + filePath);
    }

    private void storeEventInFile(String directoryPath, String filePath, String content) throws IOException {
        File directory = new File(directoryPath);
        File file = new File(filePath);
        if (!directory.exists()){
            directory.mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e){
                System.out.println("ERROR: " + e);
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        writer.write(content + "\n");
        writer.close();
    }
    private String convertDate(String inputString) {
            Instant instant = Instant.parse(inputString);
            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, java.time.ZoneOffset.UTC);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            return zonedDateTime.format(outputFormatter);
    }
}