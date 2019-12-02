package com.gjing.project.rabbitmq.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置MQ的queue
 * @author Gjing
 **/
@Configuration
public class MqConfiguration {

    //-----------direct模式，routing key必须与要发送的队列key完全符合，才会发送到这个消息队列----------------

    /**
     * 定义一个普通的队列，名称为：myQueue1
     * @return Queue
     */
    @Bean
    public Queue myQueue1() {
        return new Queue("myQueue1");
    }

    //----------以下介绍topicExchange, 模式匹配，会将消息发送到符合规则的队列中，*号表示一个单词，#号表示多个单词---------

    /**
     * 定义一个交换机
     * @return TopicExchange
     */
    @Bean
    public TopicExchange myExchange1() {
        return new TopicExchange("myExchange1");
    }

    /**
     * 定义需要与交换机绑定的队列1
     * @return Queue
     */
    @Bean
    public Queue myTopicQueue1() {
        return new Queue("topic.queue1");
    }

    /**
     * 定义需要与交换机绑定的队列2
     * @return Queue
     */
    @Bean
    public Queue myTopicQueue2() {
        return new Queue("topic.queue2");
    }

    /**
     * 将队列与交换机绑定
     * @return Binding
     */
    @Bean
    public Binding bindMessage1() {
        return BindingBuilder.bind(myTopicQueue1()).to(myExchange1()).with("topic.message");
    }

    /**
     * 将队列与交换机绑定
     * @return Binding
     */
    @Bean
    public Binding bindMessage2() {
        return BindingBuilder.bind(myTopicQueue2()).to(myExchange1()).with("topic.#");
    }


    //---------------广播模式，会把发到该交换机的消息发给所有与该交换机绑定的queue-----------------

    /**
     * 定义广播交换机
     * @return FanoutExchange
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("myFanoutExchange");
    }


    @Bean
    public Queue fanoutQueue1() {
        return new Queue("fanoutQueue1");
    }

    @Bean
    public Queue fanoutQueue2() {
        return new Queue("fanoutQueue2");
    }

    /**
     * 将几个队列绑定到交换机中
     * @return Binding
     */
    @Bean
    public Binding fanout1Bind() {
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanout2Bind() {
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }

}
