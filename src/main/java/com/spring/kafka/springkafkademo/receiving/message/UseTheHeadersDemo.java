package com.spring.kafka.springkafkademo.receiving.message;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public class UseTheHeadersDemo {

    @KafkaListener(id = "qux", topicPattern = "myTopic1")
    public void listen(@Payload String foo,
                       @Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY, required = false) Integer key,
                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts
    )
    {
        //...
    }

    // Starting with version 2.5, instead of using discrete headers,
    // you can receive record metadata in a ConsumerRecordMetadata parameter.
    @KafkaListener(id = "qux", topicPattern = "myTopic1")
    public void listen(String str, ConsumerRecordMetadata meta) {
        //...
    }

}
