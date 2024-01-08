package dacd.alejandroaleman.control;

import dacd.alejandroaleman.control.exceptions.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
import java.util.concurrent.Semaphore;

public class TopicSubscriber implements Subscriber {
    private final DatamartStore sqLiteDatamartStore;
    private Connection connection;
    private Session session;
    private final String topicName1;
    private final String topicName2;
    private final Semaphore semaphore;

    public TopicSubscriber(DatamartStore sqLiteDatamartStore, String topicName1, String topicName2, Semaphore semaphore) {
        this.sqLiteDatamartStore = sqLiteDatamartStore;
        this.topicName1 = topicName1;
        this.topicName2 = topicName2;
        this.semaphore = semaphore;
    }

    public void start() throws ReceiverException {
        try {
            ConnectionFactory connFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connFactory.createConnection();
            connection.setClientID("datamart-builder");
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic topic1 = session.createTopic(topicName1);
            MessageConsumer consumer1 = session.createDurableSubscriber(topic1, "datamart-builder" + topicName1);
            consumer1.setMessageListener(message -> processMessage(message));

            semaphore.acquire();

            Topic topic2 = session.createTopic(topicName2);
            MessageConsumer consumer2 = session.createDurableSubscriber(topic2, "datamart-builder" + topicName2);
            consumer2.setMessageListener(message -> processMessage(message));

        } catch (JMSException | InterruptedException e) {
            throw new ReceiverException("Error during message processing", e);
        }
    }

    private void processMessage(Message message) {
        try {
            String text = ((TextMessage) message).getText();
            System.out.println("[datamart-builder] Received message: " + text);
            sqLiteDatamartStore.save(text);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }
}
