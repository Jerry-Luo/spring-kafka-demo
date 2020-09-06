package com.spring.kafka.springkafkademo.receiving.message;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

/**
 * The KafkaMessageListenerContainer receives all message from all topics or partitions on a single thread.
 *
 * The ConcurrentMessageListenerContainer delegates to one or more KafkaMessageListenerContainer instances
 * to provide multi-threaded consumption.
 */
public class KafkaMessageListenerContainerDemo {

    // To assign a MessageListener to a container, you can use the
    // ContainerProps.setMessageListener method when creating the Container.
    // The following example shows how to do so:

    public KafkaMessageListenerContainer<Integer, String> createContainer(){
        ContainerProperties containerProps = new ContainerProperties("topic1", "topic2");
        containerProps.setMessageListener(new MessageListener<Integer, String>() {
            @Override
            public void onMessage(ConsumerRecord<Integer, String> data) {
                //...
            }
        });
        DefaultKafkaConsumerFactory<Integer, String> cf =
                new DefaultKafkaConsumerFactory<>(null);//(consumerProps());
        return new KafkaMessageListenerContainer<>(cf, containerProps);


        // DefaultKafkaConsumerFactory<Integer, CustomValue> cf =
        //        new DefaultKafkaConsumerFactory<>(consumerProps(), null, () -> new CustomValueDeserializer());
        // return new KafkaMessageListenerContainer<>(cf, containerProps);

        // Since version 2.1.1, a new property called logContainerConfig is available.
        // When true and INFO logging is enabled each listener container writes a log message
        // summarizing its configuration properties.

        // By default, logging of topic offset commits is performed at the DEBUG logging level.
        // Starting with version 2.1.2, a property in ContainerProperties called commitLogLevel
        // lets you specify the log level for these messages.
        // For example, to change the log level to INFO, you can use
        // containerProperties.setCommitLogLevel(LogIfLevelEnabled.Level.INFO);.

        // Starting with version 2.2, a new container property called missingTopicsFatal has been added
        // (default: false since 2.3.4). This prevents the container from starting if any of the configured
        // topics are not present on the broker.
        // It does not apply if the container is configured to listen to a topic pattern (regex).
        // Previously, the container threads looped within the consumer.poll() method waiting for the topic to appear
        // while logging many messages. Aside from the logs, there was no indication that there was a problem.


        // As of version 2.3.5, a new container property called authorizationExceptionRetryInterval has been introduced.
        // This causes the container to retry fetching messages after getting any AuthorizationException
        // from KafkaConsumer. This can happen when, for example, the configured user is denied access to read certain topic.
        // Defining authorizationExceptionRetryInterval should help the application to recover as soon as
        // proper permissions are granted.
        //
        // By default, no interval is configured - authorization errors are considered fatal,
        // which causes the container to stop.
    }
}
