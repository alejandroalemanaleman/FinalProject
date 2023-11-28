package org.control;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

public class EventReceiver {
    private String url = "tcp://localhost:61616";
    private String subject = "pruebaa";

    public List<String> recieve() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue(subject);

        MessageConsumer consumer = session.createConsumer(destination);

        List<String> events = new ArrayList<>();

        consumer.setMessageListener(message -> {
            try {
                if (message instanceof TextMessage) {
                    System.out.println("Mensaje recibido: " + ((TextMessage) message).getText());
                    events.add(((TextMessage) message).getText());
                } else {
                    System.out.println("Tipo de mensaje no compatible: " + message.getClass().getName());
                }
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("Esperando mensajes...");
        return events;
    }
}