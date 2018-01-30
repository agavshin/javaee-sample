package ru.agavshin.demo.boundary;

import org.slf4j.Logger;
import ru.agavshin.demo.entity.Demo;
import ru.agavshin.rabbit.boundary.RabbitMQService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;

@Stateless
public class DemoService {

    @Inject
    private Logger logger;

    @Inject
    private RabbitMQService rabbitMQService;

    public Demo createSample() {
        return Demo.createSample();
    }

    public void send(Demo demo) {
        try {
            rabbitMQService.sendMessage(demo);
            logger.info("Demo has been sent. {}", demo);
        } catch (IOException e) {
            logger.error("Exception while demo sending. {}", e.getMessage());
        }
    }
}
