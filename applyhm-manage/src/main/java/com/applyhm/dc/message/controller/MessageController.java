package com.applyhm.dc.message.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/hello") //"/hello"为WebSocketConfig类中registerStompEndpoints()方法配置的
    @SendTo("/topic/greetings")
    public void greeting(@Header("atytopic") String topic, @Headers Map<String, Object> headers) {
        System.out.println("connected successfully....");
        System.out.println(topic);
        System.out.println(headers);
    }

    @RequestMapping(path = "/send", method = RequestMethod.GET)
    public String send() {
        simpMessageSendingOperations.convertAndSendToUser("superadmin", "/message", "I am a msg from SubscribeMapping('/macro').");
        return "I am a msg from SubscribeMapping('/macro').";
    }

}
