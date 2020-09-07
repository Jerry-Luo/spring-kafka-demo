package com.spring.kafka.springkafkademo.receiving.message;


import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchListenersDemo {

    // Starting with version 1.1, you can configure @KafkaListener methods to
    // receive the entire batch of consumer records received from the consumer poll.
    // To configure the listener container factory to create batch listeners,
    // you can set the batchListener property. The following example shows how to do so:
    @Bean
    public KafkaListenerContainerFactory<?> batchFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchListener(true);  // <<<<<<<<<<<<<<<<<<<<<<<<<
        return factory;
    }
    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }
    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        //...
        return props;
    }


    // The following example shows how to receive a list of payloads:
    @KafkaListener(id = "list", topics = "myTopic", containerFactory = "batchFactory")
    public void listen1(List<String> list) {
        //...
    }


    // The topic, partition, offset, and so on are available in headers that parallel the payloads.
    // The following example shows how to use the headers:
    @KafkaListener(id = "list", topics = "myTopic", containerFactory = "batchFactory")
    public void listen(List<String> list,
                       @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<Integer> keys,
                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
                       @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        //...
    }

    // Alternatively, you can receive a List of Message<?> objects with each offset and other details in each message,
    // but it must be the only parameter (aside from optional Acknowledgment, when using manual commits, and/or Consumer<?, ?> parameters)
    // defined on the method. The following example shows how to do so:
    @KafkaListener(id = "listMsg", topics = "myTopic", containerFactory = "batchFactory")
    public void listen14(List<Message<?>> list) {
        //...
    }

    @KafkaListener(id = "listMsgAck", topics = "myTopic", containerFactory = "batchFactory")
    public void listen15(List<Message<?>> list, Acknowledgment ack) {
        //...
    }

    @KafkaListener(id = "listMsgAckConsumer", topics = "myTopic", containerFactory = "batchFactory")
    public void listen16(List<Message<?>> list, Acknowledgment ack, Consumer<?, ?> consumer) {
        //...
    }
    // No conversion is performed on the payloads in this case.

    // You can also receive a list of ConsumerRecord<?, ?> objects,
    // but it must be the only parameter (aside from optional Acknowledgment, when using manual commits and Consumer<?, ?> parameters)
    // defined on the method. The following example shows how to do so:
    @KafkaListener(id = "listCRs", topics = "myTopic", containerFactory = "batchFactory")
    public void listen(List<ConsumerRecord<Integer, String>> list) {
        //...
    }

    @KafkaListener(id = "listCRsAck", topics = "myTopic", containerFactory = "batchFactory")
    public void listen(List<ConsumerRecord<Integer, String>> list, Acknowledgment ack) {
        //...
    }


    // Starting with version 2.2, the listener can receive the complete ConsumerRecords<?, ?> object returned by the poll() method,
    // letting the listener access additional methods, such as partitions() (which returns the TopicPartition instances in the list)
    // and records(TopicPartition) (which gets selective records).
    // Again, this must be the only parameter (aside from optional Acknowledgment, when using manual commits or Consumer<?, ?> parameters)
    // on the method. The following example shows how to do so:
    @KafkaListener(id = "pollResults", topics = "myTopic", containerFactory = "batchFactory")
    public void pollResults(ConsumerRecords<?, ?> records) {
        //...
    }
    // 	!!! If the container factory has a RecordFilterStrategy configured, it is ignored for ConsumerRecords<?, ?> listeners,
    // 	with a WARN log message emitted.
    // 	Records can only be filtered with a batch listener if the <List<?>> form of listener is used.
}
