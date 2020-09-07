package com.spring.kafka.springkafkademo.receiving.message;

import org.springframework.core.annotation.AliasFor;
import org.springframework.kafka.annotation.KafkaListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class KafkaListenerAsMetaAnnotation {

    // Starting with version 2.2, you can now use @KafkaListener as a meta annotation.
    // The following example shows how to do so:
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @KafkaListener
    public @interface MyThreeConsumersListener {

        @AliasFor(annotation = KafkaListener.class, attribute = "id")
        String id();

        @AliasFor(annotation = KafkaListener.class, attribute = "topics")
        String[] topics();

        @AliasFor(annotation = KafkaListener.class, attribute = "concurrency")
        String concurrency() default "3";
    }

    // You must alias at least one of topics, topicPattern,
    // or topicPartitions (and, usually, id or groupId unless you have specified a group.id in the consumer factory configuration).
    // The following example shows how to do so:
    @MyThreeConsumersListener(id = "my.group", topics = "my.topic")
    public void listen1(String in) {
        //...
    }
}
