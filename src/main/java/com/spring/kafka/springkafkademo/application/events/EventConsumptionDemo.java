package com.spring.kafka.springkafkademo.application.events;

import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public class EventConsumptionDemo {

    // The event is normally published on the consumer thread,
    // so it is safe to interact with the Consumer object.

    // Note that you can obtain the current positions when idle is detected
    // by implementing ConsumerSeekAware in your listener.
    // See onIdleContainer() in Seeking to a Specific Offset.

    public class Listener {

        @KafkaListener(id = "qux", topics = "annotated")
        public void listen4(@Payload String foo, Acknowledgment ack) {
        //...
        }

        @EventListener(condition = "event.listenerId.startsWith('qux-')")
        public void eventHandler(ListenerContainerIdleEvent event) {
        //...
        }

    }
}
