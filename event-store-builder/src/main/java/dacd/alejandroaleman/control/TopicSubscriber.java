package dacd.alejandroaleman.control;

import dacd.alejandroaleman.control.exceptions.ReceiverException;
import dacd.alejandroaleman.control.exceptions.SaveException;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class TopicSubscriber implements Subscriber{
    private final EventStoreBuilder fileEventStoreBuilder;
    private Connection connection = null;
    private Session session = null;

    public TopicSubscriber(EventStoreBuilder fileEventStoreBuilder){
        this.fileEventStoreBuilder = fileEventStoreBuilder;
        /*
        String brokerUrl = "tcp://localhost:61616";
        try {
            ConnectionFactory connFactory = new ActiveMQConnectionFactory(brokerUrl);
            this.connection = connFactory.createConnection();
            connection.setClientID("event-store-builder");
            connection.start();
            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        }
        catch (JMSException e){
            throw new ReceiverException(e.getMessage());
        }
         */

    }

    public void start() throws ReceiverException {
        try{
            ConnectionFactory connFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connFactory.createConnection();
            connection.setClientID("event-store-builder");
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            String topicName = "prediction.Weather";
            Topic topic = session.createTopic(topicName);
            MessageConsumer consumer = session.createDurableSubscriber(topic, "event-store-builder_" + topicName);
            consumer.setMessageListener(message -> {
            try {
                String text = ((TextMessage) message).getText();
                fileEventStoreBuilder.save(text);
                System.out.println("Received message: " + text);
            } catch (JMSException | SaveException e) {
                throw new RuntimeException(e);
            }

        });}
        catch (JMSException e) {
            throw new ReceiverException("Error during message processing");
        }
    }
}