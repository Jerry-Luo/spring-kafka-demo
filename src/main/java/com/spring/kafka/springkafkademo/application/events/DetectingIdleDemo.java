package com.spring.kafka.springkafkademo.application.events;

public class DetectingIdleDemo {

    // You can configure the listener container to publish a ListenerContainerIdleEvent
    // when some time passes with no message delivery.
    // While the container is idle, an event is published every idleEventInterval milliseconds.

    // To configure this feature, set the idleEventInterval on the container.
    // The following example shows how to do so:

    //@Bean
    //public KafkaMessageListenerContainer(ConsumerFactory<String, String> consumerFactory) {
    //    ContainerProperties containerProps = new ContainerProperties("topic1", "topic2");
    //...
    //    containerProps.setIdleEventInterval(60000L);
    //...
    //    KafkaMessageListenerContainer<String, String> container = new KafKaMessageListenerContainer<>(...);
    //    return container;
    //}

    // The following example shows how to set the idleEventInterval for a @KafkaListener:

    //@Bean
    //public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory() {
    //    ConcurrentKafkaListenerContainerFactory<String, String> factory =
    //            new ConcurrentKafkaListenerContainerFactory<>();
    //...
    //    factory.getContainerProperties().setIdleEventInterval(60000L);
    //...
    //    return factory;
    //}
}
