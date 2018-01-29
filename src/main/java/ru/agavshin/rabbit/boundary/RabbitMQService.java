package ru.agavshin.rabbit.boundary;

import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;

@Stateless
public class RabbitMQService {

    @Inject
    private Logger logger;

    @Inject
    private Channel channel;

    public void sendMessage(Serializable message) throws IOException {
        channel.queueDeclare(AmqpConstants.QUEUE_NAME, false, false, false, null);
        channel.basicPublish("", AmqpConstants.QUEUE_NAME, null, SerializationUtils.serialize(message));
        logger.info("Message has been sent.");
    }
}
