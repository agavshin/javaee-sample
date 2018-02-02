package ru.agavshin.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.agavshin.entity.Demo;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
@EnableBinding(Processor.class)
public class DemoService {

    @Autowired
    private Logger logger;

    @Autowired
    private Processor processor;

    public Demo sample() {
        return Demo.createSample();
    }

    public void send(Demo demo) {
        logger.info("Demo has been sent. {}", demo);
        processor.output().send(MessageBuilder.withPayload(demo).build());
    }

    @StreamListener(Processor.INPUT)
    public void receive(Demo demo) {
        logger.info("Demo has been received. {}", demo);
    }
}
