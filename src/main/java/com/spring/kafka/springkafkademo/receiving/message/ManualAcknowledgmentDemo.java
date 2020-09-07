package com.spring.kafka.springkafkademo.receiving.message;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

public class ManualAcknowledgmentDemo {

    @KafkaListener(id = "cat", topics = "myTopic", containerFactory = "kafkaManualAckListenerContainerFactory")
    public void listen(String data, Acknowledgment ack) {
        //...
        ack.acknowledge();
    }

}
