package com.spring.kafka.springkafkademo.receiving.message;

public class FilteringMessages {
    // In certain scenarios, such as rebalancing, a message that has already been processed may be redelivered.
    // The framework cannot know whether such a message has been processed or not.
    // That is an application-level function.
    // This is known as the Idempotent Receiver pattern and Spring Integration provides an implementation of it.
    //
    // The Spring for Apache Kafka project also provides some assistance by means of the FilteringMessageListenerAdapter class,
    // which can wrap your MessageListener.
    // This class takes an implementation of RecordFilterStrategy in which you implement the filter method
    // to signal that a message is a duplicate and should be discarded.
    // This has an additional property called ackDiscarded,
    // which indicates whether the adapter should acknowledge the discarded record.
    // It is false by default.
    //
    // When you use @KafkaListener, set the RecordFilterStrategy (and optionally ackDiscarded)
    // on the container factory so that the listener is wrapped in the appropriate filtering adapter.
    //
    // In addition, a FilteringBatchMessageListenerAdapter is provided, for when you use a batch message listener.
    //
    // The FilteringBatchMessageListenerAdapter is ignored if your @KafkaListener
    // receives a ConsumerRecords<?, ?> instead of List<ConsumerRecord<?, ?>>,
    // because ConsumerRecords is immutable.


}
