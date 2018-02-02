package ru.agavshin.rabbit.boundary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import ru.agavshin.demo.entity.Demo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;

@Stateless
public class DemoAmqpService {

    @Inject
    private Logger logger;

    public void handle(byte[] payload) {
        String jsonString = new String(payload);
        Demo demo = new Gson().fromJson(jsonString, Demo.class);
        logger.info("Demo has been receiver. {}", demo);
    }
}
