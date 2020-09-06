package com.spring.kafka.springkafkademo.receiving.message;

/**
 * The single constructor is similar to the KafkaListenerContainer constructor.
 * The following listing shows the constructor’s signature:
 *
 * public ConcurrentMessageListenerContainer(ConsumerFactory<K, V> consumerFactory,
 *                             ContainerProperties containerProperties)
 *
 * It also has a concurrency property. For example, container.setConcurrency(3)
 * creates three KafkaMessageListenerContainer instances.
 *
 * For the first constructor, Kafka distributes the partitions across the consumers
 * using its group management capabilities.
 */
public class ConcurrentMessageListenerContainerDemo {

    // When listening to multiple topics, the default partition distribution may not be what you expect.
    // For example, if you have three topics with five partitions each and you want to use concurrency=15,
    // you see only five active consumers, each assigned one partition from each topic,
    // with the other 10 consumers being idle. This is because the default Kafka PartitionAssignor is the RangeAssignor
    // (see its Javadoc). For this scenario, you may want to consider using the RoundRobinAssignor instead,
    // which distributes the partitions across all of the consumers. Then, each consumer is assigned one topic or partition.
    // To change the PartitionAssignor, you can set the partition.assignment.strategy consumer property
    // (ConsumerConfigs.PARTITION_ASSIGNMENT_STRATEGY_CONFIG) in the properties provided to the DefaultKafkaConsumerFactory.
    //
    // When using Spring Boot, you can assign set the strategy as follows:
    // spring.kafka.consumer.properties.partition.assignment.strategy=org.apache.kafka.clients.consumer.RoundRobinAssignor



}
