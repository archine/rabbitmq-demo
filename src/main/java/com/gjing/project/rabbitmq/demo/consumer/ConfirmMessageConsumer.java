package com.gjing.project.rabbitmq.demo.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Gjing
 **/
@Component
@Slf4j
public class ConfirmMessageConsumer {

    @RabbitListener(queues = "confirm.queue")
    public void confirmQueue(String message, Message message1, Channel channel) throws IOException {
        log.info("confirmQueue收到消息：{}", message);
        long deliveryTag = message1.getMessageProperties().getDeliveryTag();
        //第一个deliveryTag参数为每条信息带有的tag值，第二个multiple参数为布尔类型
        //为true时会将小于等于此次tag的所有消息都确认掉，如果为false则只确认当前tag的信息，可根据实际情况进行选择。
        channel.basicAck(deliveryTag, true);
    }
}
