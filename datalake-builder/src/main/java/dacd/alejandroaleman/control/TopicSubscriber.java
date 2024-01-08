package dacd.alejandroaleman.control;

import dacd.alejandroaleman.control.exceptions.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.Semaphore;

public class TopicSubscriber implements Subscriber {
    private final EventStoreBuilder fileEventStoreBuilder;
    private Connection connection;
    private Session session;
    private final String topicName1;
    private final String topicName2;
    private final Semaphore semaphore;

    public TopicSubscriber(EventStoreBuilder fileEventStoreBuilder, String topicName1, String topicName2, Semaphore semaphore) {
        this.fileEventStoreBuilder = fileEventStoreBuilder;
        this.topicName1 = topicName1;
        this.topicName2 = topicName2;
        this.semaphore = semaphore;
    }

    public void start() throws ReceiverException {
        try {
            ConnectionFactory connFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connFactory.createConnection();
            connection.setClientID("datalake-builder");
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic topic1 = session.createTopic(topicName1);
            MessageConsumer consumer1 = session.createDurableSubscriber(topic1, "datalake-builder_" + topicName1);
            consumer1.setMessageListener(message -> processMessage(message, topicName1));

            semaphore.acquire();

            Topic topic2 = session.createTopic(topicName2);
            MessageConsumer consumer2 = session.createDurableSubscriber(topic2, "datalake-builder_" + topicName2);
            consumer2.setMessageListener(message -> processMessage(message, topicName2));

        } catch (JMSException | InterruptedException e) {
            throw new ReceiverException("Error during message processing", e);
        }
    }

    private void processMessage(Message message, String topicName) {
        try {
            String text = ((TextMessage) message).getText();
            fileEventStoreBuilder.save(text, topicName);
            System.out.println("[datalake-builder] Received message: " + text);
        } catch (JMSException | SaveException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }
}
