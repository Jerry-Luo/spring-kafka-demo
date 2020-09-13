package com.spring.kafka.springkafkademo.seeking.to.specific.offset;

// In order to seek, your listener must implement ConsumerSeekAware

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class SeekingSpecificffsetDemo {

    // 	The seekToBeginning method that accepts a collection is useful, for example,
    // 	when processing a compacted topic and you wish to seek to the beginning
    // 	every time the application is started:
//    public class MyListener extends AbstractConsumerSeekAware {
//
            //...
//
//        @Override
//        public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
//            callback.seekToBeginning(assignments.keySet());
//        }
//
//    }


    // Here is a trivial Spring Boot application that demonstrates how to use the callback;
    // it sends 10 records to the topic;
    // hitting <Enter> in the console causes all partitions to seek to the beginning.
    @SpringBootApplication
    public class SeekExampleApplication {

        public void main(String[] args) {
            SpringApplication.run(SeekExampleApplication.class, args);
        }

        @Bean
        public ApplicationRunner runner(Listener listener, KafkaTemplate<String, String> template) {
            return args -> {
                IntStream.range(0, 10).forEach(i -> template.send(
                        new ProducerRecord<>("seekExample", i % 3, "foo", "bar")));
                while (true) {
                    System.in.read();
                    listener.seekToStart();
                }
            };
        }

        @Bean
        public NewTopic topic() {
            return new NewTopic("seekExample", 3, (short) 1);
        }

    }

    @Component
    class Listener implements ConsumerSeekAware {

        private final Logger logger = LoggerFactory.getLogger(Listener.class);

        private final ThreadLocal<ConsumerSeekCallback> callbackForThread = new ThreadLocal<>();

        private final Map<TopicPartition, ConsumerSeekCallback> callbacks = new ConcurrentHashMap<>();

        @Override
        public void registerSeekCallback(ConsumerSeekCallback callback) {
            this.callbackForThread.set(callback);
        }

        @Override
        public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
            assignments.keySet().forEach(tp -> this.callbacks.put(tp, this.callbackForThread.get()));
        }

        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            partitions.forEach(tp -> this.callbacks.remove(tp));
            this.callbackForThread.remove();
        }

        @Override
        public void onIdleContainer(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        }

        @KafkaListener(id = "seekExample", topics = "seekExample", concurrency = "3")
        public void listen(ConsumerRecord<String, String> in) {
            logger.info(in.toString());
        }

        public void seekToStart() {
            this.callbacks.forEach((tp, callback) -> callback.seekToBeginning(tp.topic(), tp.partition()));
        }

    }


    // To make things simpler, version 2.3 added the AbstractConsumerSeekAware class,
    // which keeps track of which callback is to be used for a topic/partition.
    // The following example shows how to seek to the last record processed,
    // in each partition, each time the container goes idle.
    // It also has methods that allow arbitrary external calls to rewind partitions by one record.
    public class SeekToLastOnIdleListener extends AbstractConsumerSeekAware {

        @KafkaListener(id = "seekOnIdle", topics = "seekOnIdle")
        public void listen(String in) {
        //...
        }

        @Override
        public void onIdleContainer(Map<org.apache.kafka.common.TopicPartition, Long> assignments,
                                    ConsumerSeekCallback callback) {

            assignments.keySet().forEach(tp -> callback.seekRelative(tp.topic(), tp.partition(), -1, true));
        }

        /**
         * Rewind all partitions one record.
         */
        public void rewindAllOneRecord() {
            getSeekCallbacks()
                    .forEach((tp, callback) ->
                            callback.seekRelative(tp.topic(), tp.partition(), -1, true));
        }

        /**
         * Rewind one partition one record.
         */
        public void rewindOnePartitionOneRecord(String topic, int partition) {
            getSeekCallbackFor(new org.apache.kafka.common.TopicPartition(topic, partition))
                    .seekRelative(topic, partition, -1, true);
        }

    }
}
