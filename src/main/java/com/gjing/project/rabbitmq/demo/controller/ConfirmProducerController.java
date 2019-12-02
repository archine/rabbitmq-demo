package com.gjing.project.rabbitmq.demo.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author Gjing
 **/
@RestController
@Slf4j
public class ConfirmProducerController {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/confirm_queue")
    @ApiOperation("发送带回调的消息")
    @ApiImplicitParam(name = "message", value = "消息内容", dataType = "String", required = true, paramType = "query")
    public ResponseEntity confirmQueue(String message) {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString().replace("-", ""));
        Message message1 = MessageBuilder.withBody(message.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setCorrelationId(correlationData.getId())
                .build();
        rabbitTemplate.setConfirmCallback(this.confirmCallback);
        rabbitTemplate.setReturnCallback(this.returnCallback);
        rabbitTemplate.convertAndSend("directExchange","confirm.message", message1,correlationData);
        return ResponseEntity.ok("发送成功");
    }

    /**
     * 如果消息没有到exchange,则confirm回调,ack=false
     * 如果消息到达exchange,则confirm回调,ack=true
     * exchange到queue成功,则不回调return
     * exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
     */
    private final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        if (!ack) {
            log.error("消息发送失败，消息内容：{}，错误信息: {}", correlationData, ack);
        }
    };

    private final RabbitTemplate.ReturnCallback returnCallback = (message, replyCode, replyText, exchange, routeKey)
            -> log.error("消息丢失，message：{}, replyCode：{}, replyTest: {}, exchange: {}, routeKey: {}",message,replyCode,replyText,exchange,routeKey);
}
