package dacd.alejandroaleman.control;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
public class Prueba {

    private String brokerUrl = "tcp://localhost:61616";
    private String topicName = "prueba.Weather2";
    private String eventStoreDirectory = "eventstorePrueba2";


    public void subscribeAndStoreEvents() throws JMSException {
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
    public static void store(String json) throws IOException{
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String tsString = jsonObject.get("ts").getAsString();
        Instant ts = Instant.parse(tsString);
        String ss = jsonObject.get("ss").getAsString();
        LocalDate date = ts.atZone(ZoneOffset.UTC).toLocalDate();

        String basePath = "eventstore/prediction.Weather/" + ss + "/";
        String filePath = basePath  + date.toString() + ".events";

        File directory = new File(basePath);
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
        writer.write(json + "\n");
        writer.close();
        System.out.println("Event stored at: " + filePath);
    }
}
//CAMBIAR CLASE ; ADAPTARLO A EVENT RECEIVER