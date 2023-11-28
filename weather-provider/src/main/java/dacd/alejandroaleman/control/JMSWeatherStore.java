package dacd.alejandroaleman.control;

import com.google.gson.*;
import dacd.alejandroaleman.model.Weather;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.List;

public class JMSWeatherStore implements WeatherStore{

    public void save(List<Weather> weathers) throws JMSException {
        String url = "tcp://localhost:61616";
        String subject = "prueba1";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        for (Weather weather : weathers) {
            String event = getAsJson(weather);
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(subject);
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session
                    .createTextMessage(event);
            producer.send(message);
            System.out.println("JCG printing@@ '" + message.getText() + "'");
        }
        connection.close();
    }

     private String getAsJson(Weather weather){
         Gson gson = new GsonBuilder()
                 .registerTypeAdapter(Instant.class, new InstantSerializer())
                 .create();
        return gson.toJson(weather);
    }

    public class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.toString());
        }
    }
}
