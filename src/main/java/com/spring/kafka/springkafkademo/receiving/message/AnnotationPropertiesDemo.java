package com.spring.kafka.springkafkademo.receiving.message;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;

public class AnnotationPropertiesDemo {

    // Starting with version 2.0, the id property (if present) is used as the Kafka consumer group.id property,
    // overriding the configured property in the consumer factory, if present.
    // You can also set groupId explicitly or set idIsGroup to false to restore the previous behavior of using the consumer factory group.id.

    // You can use property placeholders or SpEL expressions within most annotation properties, as the following example shows:
    @KafkaListener(topics = "${some.property}")
    public void listen(List<ConsumerRecord<Integer, String>> list) {
        //...
    }
    @KafkaListener(topics = "#{someBean.someProperty}",
            groupId = "#{someBean.someProperty}.group")
    public void listen1(List<ConsumerRecord<Integer, String>> list) {
        //...
    }


    // Starting with version 2.1.2, the SpEL expressions support a special token: __listener.
    // It is a pseudo bean name that represents the current bean instance within which this annotation exists.
    // Consider the following example:
    @Bean
    public Listener listener1() {
        return new Listener("topic1");
    }

    @Bean
    public Listener listener2() {
        return new Listener("topic2");
    }

    // Given the beans in the previous example, we can then use the following:
    public class Listener {

        private final String topic;

        public Listener(String topic) {
            this.topic = topic;
        }

        @KafkaListener(topics = "#{__listener.topic}", groupId = "#{__listener.topic}.group")
        public void listen(List<ConsumerRecord<Integer, String>> list) {
            //...
        }

        public String getTopic() {
            return this.topic;
        }
    }

    // If, in the unlikely event that you have an actual bean called __listener,
    // you can change the expression token byusing the beanRef attribute.
    // The following example shows how to do so:
    @KafkaListener(beanRef = "__x", topics = "#{__x.topic}", groupId = "#{__x.topic}.group")
    public void listen2(List<ConsumerRecord<Integer, String>> list) {
        //...
    }

    // Starting with version 2.2.4, you can specify Kafka consumer properties directly on the annotation,
    // these will override any properties with the same name configured in the consumer factory.
    // You cannot specify the group.id and client.id properties this way; they will be ignored;
    // use the groupId and clientIdPrefix annotation properties for those.
    //
    // The properties are specified as individual strings with the
    // normal Java Properties file format: foo:bar, foo=bar, or foo bar.
    @KafkaListener(topics = "myTopic", groupId = "group", properties = {
            "max.poll.interval.ms:60000",
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG + "=100"
    })
    public void listen3(List<ConsumerRecord<Integer, String>> list) {
        //...
    }

    // The following is an example of the corresponding listeners for the example in Using RoutingKafkaTemplate.
    @KafkaListener(id = "one", topics = "one")
    public void listen1(String in) {
        System.out.println("1: " + in);
    }

    @KafkaListener(id = "two", topics = "two",
            properties = "value.deserializer:org.apache.kafka.common.serialization.ByteArrayDeserializer")
    public void listen2(byte[] in) {
        System.out.println("2: " + new String(in));
    }

}
