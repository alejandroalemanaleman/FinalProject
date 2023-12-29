package dacd.alejandroaleman.control;

import dacd.alejandroaleman.control.exceptions.ReceiverException;

public class DatamartController {
    private TopicSubscriber topicConsumer;

    public DatamartController(TopicSubscriber topicConsumer) {
        this.topicConsumer = topicConsumer;
    }

    public void excute() throws ReceiverException {
        topicConsumer.start();
    }
}
