package com.spring.kafka.springkafkademo.sending.message;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Bean
    public ProducerFactory<Integer, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // See https://kafka.apache.org/documentation/#producerconfigs for more properties
        return props;
    }

    @Bean
    public KafkaTemplate<Integer, String> kafkaTemplate() {
        return new KafkaTemplate<Integer, String>(producerFactory());
    }


    // Starting with version 2.5, you can now override the factory’s ProducerConfig properties to create templates
    // with different producer configurations from the same factory.
    @Bean
    public KafkaTemplate<String, String> stringTemplate(ProducerFactory<String, String> pf) {
        return new KafkaTemplate<>(pf);
    }

    @Bean
    public KafkaTemplate<String, byte[]> bytesTemplate(ProducerFactory<String, byte[]> pf) {
        return new KafkaTemplate<>(pf,
                Collections.singletonMap(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class));
    }
    // Note that a bean of type ProducerFactory<?, ?> (such as the one auto-configured by Spring Boot) can be referenced
    // with different narrowed generic types.


    // Notice that the send methods return a ListenableFuture<SendResult>.
    // You can register a callback with the listener to receive the result of the send asynchronously.
    // The following example shows how to do so:
    //    ListenableFuture<SendResult<Integer, String>> future = template.send("myTopic", "something");
    //    future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
    //
    //        @Override
    //        public void onSuccess(SendResult<Integer, String> result) {
    //        ...
    //        }
    //
    //        @Override
    //        public void onFailure(Throwable ex) {
    //        ...
    //        }
    //    });

    //You can also use a pair of lambdas:
    //    ListenableFuture<SendResult<Integer, String>> future = template.send("topic", 1, "thing");
    //    future.addCallback(
    //      result -> {
    //        ...
    //      },
    //      (KafkaFailureCallback<Integer, String>) ex -> {
    //        ProducerRecord<Integer, String> failed = ex.getFailedProducerRecord();
    //            ...
    //      }
    //    );



}
