package com.spring.kafka.springkafkademo.sending.message.examples;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;

public class ReplyTypeMessageDemo {

    @KafkaListener(id = "requestor", topics = "request")
    @SendTo
    public Message<?> messageReturn(String in) {
        return MessageBuilder.withPayload(in.toUpperCase())
                .setHeader(KafkaHeaders.TOPIC, "replyTo")
                .setHeader(KafkaHeaders.MESSAGE_KEY, 42)
                .setHeader(KafkaHeaders.CORRELATION_ID, "correlation")
                .build();
    }

    @KafkaListener(id = "requestor", topics = "request")
    @SendTo  // default REPLY_TOPIC header
    public Message<?> messageReturn1(String in) {
        return MessageBuilder.withPayload(in.toUpperCase())
                .setHeader(KafkaHeaders.MESSAGE_KEY, 42)
                .build();
    }
}
