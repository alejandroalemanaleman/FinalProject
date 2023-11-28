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
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        for (Weather weather : weathers) {
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);

            //Destination represents here our queue 'JCG_QUEUE' on the JMS server.
            //The queue will be created automatically on the server.
            String subject = "TESTING_NOW";
            Destination destination = session.createQueue(subject);

            // MessageProducer is used for sending messages to the queue.
            MessageProducer producer = session.createProducer(destination);

            // We will send a small text message saying 'Hello World!!!'
            TextMessage message = session
                    .createTextMessage(getAsJson(weather));

            // Here we are sending our message!
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

    private static class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.toString());
        }
    }
}
