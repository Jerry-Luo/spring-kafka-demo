package com.spring.kafka.springkafkademo.receiving.message;

import org.springframework.kafka.annotation.KafkaListener;

// The @KafkaListener annotation is used to designate a bean method as a listener for a listener container.
// The bean is wrapped in a MessagingMessageListenerAdapter configured with various features,
// such as converters to convert the data, if necessary, to match the method parameters.
public class KafkaListenerDemo {

    @KafkaListener(id = "foo", topics = "myTopic", clientIdPrefix = "myClientId")
    public void listen(String data) {
        //...
    }

    @KafkaListener(id = "myListener", topics = "myTopic",
            autoStartup = "${listen.auto.start:true}", concurrency = "${listen.concurrency:3}")
    public void listen1(String data) {
        //...
    }
}
