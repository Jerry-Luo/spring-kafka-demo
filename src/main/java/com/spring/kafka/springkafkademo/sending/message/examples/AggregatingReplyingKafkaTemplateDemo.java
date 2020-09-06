package com.spring.kafka.springkafkademo.sending.message.examples;

public class AggregatingReplyingKafkaTemplateDemo {

    // AggregatingReplyingKafkaTemplate<Integer, String, String> template =
    //         new AggregatingReplyingKafkaTemplate<>(producerFactory, container,
    //                 coll -> coll.size() == releaseSize);
    // ...
    // RequestReplyFuture<Integer, String, Collection<ConsumerRecord<Integer, String>>> future =
    //             template.sendAndReceive(record);
    // future.getSendFuture().get(10,TimeUnit.SECONDS); // send ok
    // ConsumerRecord<Integer, Collection<ConsumerRecord<Integer, String>>> consumerRecord =
    //             future.get(30, TimeUnit.SECONDS);
}
