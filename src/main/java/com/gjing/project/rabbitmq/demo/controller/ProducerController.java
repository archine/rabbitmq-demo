package com.gjing.project.rabbitmq.demo.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Gjing
 **/
@RestController
public class ProducerController {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/queue1")
    @ApiOperation("发送消息到队列1")
    @ApiImplicitParam(name = "message",value = "消息内容",dataType = "String",required = true,paramType = "query")
    public ResponseEntity sendQueue1(String message) {
        rabbitTemplate.convertAndSend("myQueue1", message);
        return ResponseEntity.ok("发送成功");
    }

    @PostMapping("/topic_exchange")
    @ApiOperation("给模糊匹配交换机发送消息")
    @ApiImplicitParam(name = "message", value = "消息内容", dataType = "String", required = true, paramType = "query")
    public ResponseEntity sendExchange(String message) {
        rabbitTemplate.convertAndSend("myExchange1", "topic.message", message+"1");
        return ResponseEntity.ok("发送成功");
    }

    @PostMapping("/fanout_exchange")
    @ApiOperation("广播发消息")
    @ApiImplicitParam(name = "message", value = "消息内容", dataType = "String", required = true, paramType = "query")
    public ResponseEntity sendFanout(String message) {
        rabbitTemplate.convertAndSend("myFanoutExchange", "", message);
        return ResponseEntity.ok("发送成功");
    }
}
