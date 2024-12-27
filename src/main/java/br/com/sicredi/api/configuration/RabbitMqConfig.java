package br.com.sicredi.api.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;


@Component
public class RabbitMqConfig {

    public static final String RULING_QUEUE = "ruling";
    public static final String EXCHANGE_NAME = "amq.direct";
    private AmqpAdmin amqpAdmin;

    private RabbitMqConfig(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue setQueue(String nameQueue) {
        return new Queue(nameQueue, true);
    }

    private DirectExchange setNameExchange(String name) {
        return new DirectExchange(name);
    }

    private Binding binding(Queue queue, DirectExchange directExchange) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, directExchange.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void submit() {
        Queue rulingQueue = this.setQueue(RULING_QUEUE);
        DirectExchange directExchange = this.setNameExchange(EXCHANGE_NAME);
        Binding binding = this.binding(rulingQueue, directExchange);

        this.amqpAdmin.declareQueue(rulingQueue);
        this.amqpAdmin.declareExchange(directExchange);
        this.amqpAdmin.declareBinding(binding);
    }
}
