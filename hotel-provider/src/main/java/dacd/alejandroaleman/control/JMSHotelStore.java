package dacd.alejandroaleman.control;

import com.google.gson.*;
//import dacd.alejandroaleman.control.exceptions.StoreException;
import dacd.alejandroaleman.model.Hotel;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.List;

public class JMSHotelStore implements HotelStore {

    private Connection connection;
    private Session session;
    public void save(List<Hotel> hotels) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            String topic = "prueba.Hotel";
            Destination destination = session.createTopic(topic);
            MessageProducer producer = session.createProducer(destination);

            for (Hotel hotel : hotels) {
                TextMessage message = session.createTextMessage(getAsJson(hotel));
                producer.send(message);
                System.out.println("[hotel-provider] MESSAGE SENT: '" + message.getText() + "' to: " + topic);
            }

            session.close();
            connection.close();
        } catch (JMSException e) {
            //throw new StoreException(e.getMessage());
        }
    }

    private String getAsJson(Hotel hotel){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantSerializer())
                .create();
        return gson.toJson(hotel);
    }

    private static class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.toString());
        }
    }
}
