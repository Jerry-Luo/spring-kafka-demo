package com.spring.kafka.springkafkademo.container.factory;

public class ContainerFactory {

    // Starting with version 2.2, you can use the same factory to create any ConcurrentMessageListenerContainer.
    // This might be useful if you want to create several containers with similar properties
    // or you wish to use some externally configured factory, such as the one provided by Spring Boot auto-configuration.
    // Once the container is created, you can further modify its properties,
    // many of which are set by using container.getContainerProperties().
    // The following example configures a ConcurrentMessageListenerContainer:

    //@Bean
    //public ConcurrentMessageListenerContainer<String, String>(
    //    ConcurrentKafkaListenerContainerFactory<String, String> factory) {
    //
    //    ConcurrentMessageListenerContainer<String, String> container =
    //            factory.createContainer("topic1", "topic2");
    //    container.setMessageListener(m -> { ... } );
    //    return container;
    //}
    // 	Containers created this way are not added to the endpoint registry.
    // 	They should be created as @Bean definitions so that they are registered with the application context.

    // Starting with version 2.3.4, you can add a ContainerCustomizer to the factory to further configure each container
    // after it has been created and configured.
    // @Bean
    // public KafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory() {
    //     ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
    //             new ConcurrentKafkaListenerContainerFactory<>();
    //     ...
    //     factory.setContainerCustomizer(container -> { /* customize the container */ });
    //     return factory;
    // }


}
