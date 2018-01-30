package ru.agavshin.service;

import org.springframework.stereotype.Service;
import ru.agavshin.entity.Demo.Demo;

@Service
public class DemoService {

    public Demo sample() {
        return Demo.createSample();
    }
}
