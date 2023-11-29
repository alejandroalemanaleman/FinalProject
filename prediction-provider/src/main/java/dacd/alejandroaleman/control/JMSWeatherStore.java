package dacd.alejandroaleman.control;

import com.google.gson.*;
import dacd.alejandroaleman.exceptions.StoreException;
import dacd.alejandroaleman.model.Weather;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.List;

public class JMSWeatherStore implements WeatherStore{

    public void save(List<Weather> weathers) throws StoreException {
        try{
        String url = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        for (Weather weather : weathers) {
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            String subject = "prueba.Weather2";
            Destination destination = session.createTopic(subject);
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session
                    .createTextMessage(getAsJson(weather));
            producer.send(message);

            System.out.println("JCG printing@@ '" + message.getText() + "'");
        }
        connection.close();}
        catch (JMSException e){
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
