package com.example.queueexample;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import static com.example.queueexample.Configs.HOST;
import static com.example.queueexample.Configs.TASK_QUEUE_NAME;

public class Sender {


    private ConnectionFactory factory;
    private final boolean durable = true;
    private Channel channel;
    private Connection connection;

    public Sender() {
        factory = new ConnectionFactory();

    }

    public void createConnection() throws IOException, TimeoutException {
        factory.setHost(HOST);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
    }

    public void sendMessage(String message) throws IOException {
        channel.basicPublish("", TASK_QUEUE_NAME,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + message + "'");
    }
}

