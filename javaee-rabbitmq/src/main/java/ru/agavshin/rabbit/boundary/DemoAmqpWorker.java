package ru.agavshin.rabbit.boundary;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import ru.agavshin.demo.entity.Demo;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeoutException;

@Startup
@Singleton
public class DemoAmqpWorker {

    private ConnectionFactory connectionFactory;

    @Inject
    private DemoAmqpService amqpService;

    private Channel channel;

    @Inject
    private Logger logger;

    @Resource
    private ManagedExecutorService executorService;

    @PostConstruct
    public void init() {
        try {
            initChannel();
        } catch (IOException | TimeoutException e) {
            logger.error("Error occurred while channel initialization. {}", e.getMessage());
        }
    }

    private ConnectionFactory getConnectionFactory() {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
            connectionFactory.setUsername(AmqpConstants.USERNAME);
            connectionFactory.setPassword(AmqpConstants.PASSWORD);
            connectionFactory.setVirtualHost(AmqpConstants.VIRTUAL_HOST);
            connectionFactory.setHost(AmqpConstants.HOST);
            connectionFactory.setPort(AmqpConstants.PORT);
        }
        return connectionFactory;
    }

    private void initChannel() throws IOException, TimeoutException {
        if (channel != null) {
            return;
        }
        Connection connection = getConnectionFactory().newConnection(executorService);
        channel = connection.createChannel();
        channel.exchangeDeclare(AmqpConstants.EXCHANGE_NAME, "topic", true);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, AmqpConstants.EXCHANGE_NAME, "#");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                amqpService.handle(body);
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }

    public void sendDemo(Demo demo) throws IOException {
        AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
        properties.contentType(MediaType.APPLICATION_JSON);
        properties.headers(Collections.singletonMap("type", "demo"));
        String json = new Gson().toJson(demo);
        channel.basicPublish(AmqpConstants.EXCHANGE_NAME, "#", null, json.getBytes());
        logger.info("Message has been sent.");
    }
}
