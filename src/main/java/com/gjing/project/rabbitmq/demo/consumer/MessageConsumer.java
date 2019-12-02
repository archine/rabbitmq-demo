package com.gjing.project.rabbitmq.demo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Gjing
 **/
@Component
@Slf4j
public class MessageConsumer {
    /**
     * 如果同时声明多个监听者监听同一个队列，那么只会调取其中一个
     * @param message 消息内容
     */
    @RabbitListener(queues = "myQueue1")
    public void listenQueue1(String message) {
        log.info("收到队列1的消息：{}", message);
    }

    //-------------------------

    /**
     * 设置模糊匹配的队列监听者
     * @param message 消息内容
     */
    @RabbitListener(queues = "topic.queue1")
    public void listenTopicQueue1(String message) {
        log.info("topicQueue1收到消息: {}", message);
    }

    @RabbitListener(queues = "topic.queue2")
    public void listenTopicQueue2(String message) {
        log.info("topicQueue2收到消息: {}", message);
    }

    //----------------------------

    /**
     * 设置广播的队列监听者
     * @param message 消息内容
     */
    @RabbitListener(queues = "fanoutQueue1")
    public void listenFanoutQueue1(String message) {
        log.info("fanoutQueue1收到消息：{}", message);
    }

    @RabbitListener(queues = "fanoutQueue2")
    public void listenFanoutQueue2(String message) {
        log.info("fanoutQueue2收到消息：{}", message);
    }

}
