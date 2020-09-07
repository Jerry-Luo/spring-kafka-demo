package com.spring.kafka.springkafkademo.receiving.message;

public class ContainerThreadNaming {

    // Listener containers currently use two task executors, one to invoke the consumer
    // and another that is used to invoke the listener when the kafka consumer property enable.auto.commit is false.
    // You can provide custom executors by setting the consumerExecutor and
    // listenerExecutor properties of the container’s ContainerProperties.
    // When using pooled executors, be sure that enough threads are available to
    // handle the concurrency across all the containers in which they are used.
    // When using the ConcurrentMessageListenerContainer, a thread from each is used for each consumer (concurrency).
    //
    //If you do not provide a consumer executor, a SimpleAsyncTaskExecutor is used.
    // This executor creates threads with names similar to <beanName>-C-1 (consumer thread).
    // For the ConcurrentMessageListenerContainer, the <beanName> part of the thread name becomes <beanName>-m,
    // where m represents the consumer instance. n increments each time the container is started.
    // So, with a bean name of container, threads in this container will be named container-0-C-1, container-1-C-1 etc.,
    // after the container is started the first time; container-0-C-2, container-1-C-2 etc., after a stop and subsequent start.
}
