package dacd.alejandroaleman.control;

import dacd.alejandroaleman.control.exceptions.ReceiverException;
import dacd.alejandroaleman.control.exceptions.SaveException;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class TopicSubscriber implements Subscriber{
    private final EventStoreBuilder fileEventStoreBuilder;
    private Connection connection;
    private Session session;
    private final String topicName;

    public TopicSubscriber(EventStoreBuilder fileEventStoreBuilder, String topicName){
        this.fileEventStoreBuilder = fileEventStoreBuilder;
        this.topicName = topicName; //TODO
    }

    public void start() throws ReceiverException {
        try{
            ConnectionFactory connFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connFactory.createConnection();
            connection.setClientID("datalake-builder");
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(topicName);
            MessageConsumer consumer = session.createDurableSubscriber(topic, "event-store-builder_" + topicName);
            consumer.setMessageListener(message -> {
            try {
                String text = ((TextMessage) message).getText();
                fileEventStoreBuilder.save(text);
                System.out.println("[datalake-builder] Received message: " + text);
            } catch (JMSException | SaveException e) {
                throw new RuntimeException(e);
            }

        });}
        catch (JMSException e) {
            throw new ReceiverException("Error during message processing");
        }
    }
}