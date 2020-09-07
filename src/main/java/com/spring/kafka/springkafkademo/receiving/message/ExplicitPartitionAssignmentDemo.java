package com.spring.kafka.springkafkademo.receiving.message;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;

public class ExplicitPartitionAssignmentDemo {

    @KafkaListener(
        id = "thing2",
        topicPartitions = {
            @TopicPartition(topic = "topic1", partitions = { "0", "1" }),
            @TopicPartition(topic = "topic2", partitions = "0",
                            partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "100")
            )
        }
    )
    public void listen(ConsumerRecord<?, ?> record) {
        //...
    }


    // Starting with version 2.5.5, you can apply an initial offset to all assigned partitions:
    @KafkaListener(id = "thing3", topicPartitions =
            { @TopicPartition(topic = "topic1", partitions = { "0", "1" },
                    partitionOffsets = @PartitionOffset(partition = "*", initialOffset = "0"))
            })
    public void listen1(ConsumerRecord<?, ?> record) {
        //...
    }

    // In addition, when the listener implements ConsumerSeekAware, onPartitionsAssigned is now called,
    // even when using manual assignment. This allows, for example, any arbitrary seek operations at that time.
}
