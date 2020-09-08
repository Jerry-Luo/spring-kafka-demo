package com.spring.kafka.springkafkademo.receiving.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;

public class KafkaListenerLifecycleManagement {

    // The listener containers created for @KafkaListener annotations are not beans in the application context.
    // Instead, they are registered with an infrastructure bean of type KafkaListenerEndpointRegistry.
    // This bean is automatically declared by the framework and manages the containers' lifecycles;
    // it will auto-start any containers that have autoStartup set to true.
    // All containers created by all container factories must be in the same phase.
    // See Listener Container Auto Startup for more information.
    // You can manage the lifecycle programmatically by using the registry.
    // Starting or stopping the registry will start or stop all the registered containers.
    // Alternatively, you can get a reference to an individual container by using its id attribute.
    // You can set autoStartup on the annotation, which overrides the default setting configured into the container factory.
    // You can get a reference to the bean from the application context, such as auto-wiring, to manage its registered containers.
    // The following examples show how to do so:
    @KafkaListener(id = "myContainer", topics = "myTopic", autoStartup = "false")
    public void listen(String payload) {
        //...
    }

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    //...
    public void init(){
        this.registry.getListenerContainer("myContainer").start();
    }
    //...

    // The registry only maintains the life cycle of containers it manages;
    // containers declared as beans are not managed by the registry and can be obtained from the application context.
    // A collection of managed containers can be obtained by calling the registry’s getListenerContainers() method.
    // Version 2.2.5 added a convenience method getAllListenerContainers(), which returns a collection of all containers,
    // including those managed by the registry and those declared as beans.
    // The collection returned will include any prototype beans that have been initialized,
    // but it will not initialize any lazy bean declarations.
}
