package ru.agavshin.rabbit.boundary;

public interface AmqpConstants {

    String USERNAME = "guest";
    String PASSWORD = "guest";
    String VIRTUAL_HOST = "/";
    String HOST = "localhost";
    int PORT = 5672;

    String QUEUE_NAME = "javaee-test-queue";
    String EXCHANGE_NAME = "demo-queue-out";
}
