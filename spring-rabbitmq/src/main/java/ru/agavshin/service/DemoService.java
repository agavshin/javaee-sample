package ru.agavshin.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agavshin.entity.Demo;

import static org.springframework.util.Assert.*;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
@EnableBinding(Processor.class)
public class DemoService {

    private final Logger logger;

    private final Processor processor;

    @Autowired
    public DemoService(Logger logger, Processor processor) {
        notNull(logger, "Error logger injecting.");
        this.logger = logger;
        notNull(processor, "Error processor injecting.");
        this.processor = processor;
    }

    public Demo sample() {
        return Demo.createSample();
    }

    public void send(Demo demo) {
        logger.info("Demo has been sent. {}", demo);
        String json = new Gson().toJson(demo);
        processor.output().send(MessageBuilder.withPayload(json.getBytes()).build());
    }

    @StreamListener(Processor.INPUT)
    public void receive(String json) {
        Demo demo = new Gson().fromJson(json, Demo.class);
        logger.info("Demo has been received. {}", demo);
    }
}
