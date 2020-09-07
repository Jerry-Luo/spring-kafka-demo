package com.spring.kafka.springkafkademo.receiving.message;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

public class KafkaListenerOnClass {
    // When you use @KafkaListener at the class-level, you must specify @KafkaHandler at the method level.
    // When messages are delivered, the converted message payload type is used to determine which method to call.
    // The following example shows how to do so:
    @KafkaListener(id = "multi", topics = "myTopic")
    static class MultiListenerBean {

        @KafkaHandler
        public void listen(String foo) {
            //...
        }

        @KafkaHandler
        public void listen(Integer bar) {
            //...
        }

        @KafkaHandler(isDefault = true)
        public void listenDefault(Object object) {
            //...
        }

    }
    // Starting with version 2.1.3, you can designate a @KafkaHandler method as the default method that is invoked
    // if there is no match on other methods. At most, one method can be so designated.
    // When using @KafkaHandler methods, the payload must have already been converted to the domain object
    // (so the match can be performed). Use a custom deserializer, the JsonDeserializer, or the JsonMessageConverter
    // with its TypePrecedence set to TYPE_ID. See Serialization, Deserialization, and Message Conversion for more information.

    // Due to some limitations in the way Spring resolves method arguments,
    // a default @KafkaHandler cannot receive discrete headers;
    // it must use the ConsumerRecordMetadata as discussed in Consumer Record Metadata.
    // For example:
    @KafkaHandler(isDefault = true)
    public void listenDefault(Object object, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        //...
    }
    // This won’t work if the object is a String; the topic parameter will also get a reference to object.
    // If you need metadata about the record in a default method, use this
}
