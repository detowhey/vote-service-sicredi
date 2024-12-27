package br.com.sicredi.api.service;

public interface IRabbitMqService {
    void sendMessage(String queueName, Object message);
}
