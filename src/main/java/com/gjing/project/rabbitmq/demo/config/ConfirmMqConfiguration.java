package com.gjing.project.rabbitmq.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ回调
 * @author Gjing
 **/
@Configuration
public class ConfirmMqConfiguration {

    @Bean
    public Queue confirmQueue() {
        return new Queue("confirm.queue");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    @Bean
    public Binding directBind() {
        return BindingBuilder.bind(confirmQueue()).to(directExchange()).with("confirm.message");
    }
}
