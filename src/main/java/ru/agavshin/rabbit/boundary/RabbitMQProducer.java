package ru.agavshin.rabbit.boundary;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import ru.agavshin.demo.entity.Demo;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
public class RabbitMQProducer {

    @Inject
    private Logger logger;

    @Resource
    private ManagedExecutorService executorService;

    @Produces
    private Channel getChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(AmqpConstants.USERNAME);
        factory.setPassword(AmqpConstants.PASSWORD);
        factory.setVirtualHost(AmqpConstants.VIRTUAL_HOST);
        factory.setHost(AmqpConstants.HOST);
        factory.setPort(AmqpConstants.PORT);

        Connection connection = factory.newConnection(executorService);
        Channel channel = connection.createChannel();

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                Demo message = SerializationUtils.deserialize(body);
                logger.info("Message has been received. {}", message.toString());
            }
        };
        channel.basicConsume(AmqpConstants.QUEUE_NAME, true, consumer);

        return channel;
    }
}
