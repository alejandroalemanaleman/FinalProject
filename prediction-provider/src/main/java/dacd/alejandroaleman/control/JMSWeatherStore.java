package dacd.alejandroaleman.control;

import com.google.gson.*;
import dacd.alejandroaleman.control.exceptions.StoreException;
import dacd.alejandroaleman.model.Weather;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.List;

public class JMSWeatherStore implements WeatherStore{
    private Connection connection;
    private Session session;
    public void save(List<Weather> weathers) throws StoreException {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            connection.start();

            // Create session outside the loop
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            String subject = "prediction.Weather";
            Destination destination = session.createTopic(subject);
            MessageProducer producer = session.createProducer(destination);

            for (Weather weather : weathers) {
                TextMessage message = session.createTextMessage(getAsJson(weather));
                producer.send(message);
                System.out.println("[prediction-provider] MESSAGE SENT: '" + message.getText() + "' to: " + subject);
            }

            // Close the session after the loop
            session.close();
            connection.close();
        } catch (JMSException e) {
            throw new StoreException(e.getMessage());
        }
    }

    private String getAsJson(Weather weather){
         Gson gson = new GsonBuilder()
                 .registerTypeAdapter(Instant.class, new InstantSerializer())
                 .create();
        return gson.toJson(weather);
    }

    private static class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.toString());
        }
    }
}
