package com.spring.kafka.springkafkademo.receiving.message;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.adapter.ReplyHeadersConfigurer;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ForwardingListenerResultsUsingSendTo {

    @KafkaListener(topics = "annotated21")
    @SendTo("!{request.value()}") // runtime SpEL
    public String replyingListener(String in) {
        //...
        return null;
    }

    @KafkaListener(topics = "${some.property:annotated22}")
    @SendTo("#{myBean.replyTopic}") // config time SpEL
    public Collection<String> replyingBatchListener(List<String> in) {
        //...
        return null;
    }

    @KafkaListener(topics = "annotated23", errorHandler = "replyErrorHandler")
    @SendTo("annotated23reply") // static reply topic definition
    public String replyingListenerWithErrorHandler(String in) {
        //...
        return null;
    }
    //...
    @KafkaListener(topics = "annotated25")
    @SendTo("annotated25reply1")
    public class MultiListenerSendTo {

        @KafkaHandler
        public String foo(String in) {
            //...
            return null;
        }

        @KafkaHandler
        @SendTo("!{'annotated25reply2'}")
        public String bar(@Payload(required = false) KafkaNull nul,
                          @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) int key) {
            //...
            return null;
        }

    }

    // The following example shows how to add a ReplyHeadersConfigurer:
    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        //factory.setConsumerFactory(cf());
        //factory.setReplyTemplate(template());
        factory.setReplyHeadersConfigurer((k, v) -> k.equals("cat"));
        return factory;
    }

    // You can also add more headers if you wish. The following example shows how to do so:
    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory1() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        //factory.setConsumerFactory(cf());
        //factory.setReplyTemplate(template());
        factory.setReplyHeadersConfigurer(new ReplyHeadersConfigurer() {

            @Override
            public boolean shouldCopy(String headerName, Object headerValue) {
                return false;
            }

            @Override
            public Map<String, Object> additionalHeaders() {
                return Collections.singletonMap("qux", "fiz");
            }

        });
        return factory;
    }
    // When you use @SendTo, you must configure the ConcurrentKafkaListenerContainerFactory with a KafkaTemplate
    // in its replyTemplate property to perform the send.


    // Unless you use request/reply semantics only the simple send(topic, value) method is used,
    // so you may wish to create a subclass to generate the partition or key.
    // The following example shows how to do so:
    //@Bean
    //public KafkaTemplate<String, String> myReplyingTemplate() {
    //    return new KafkaTemplate<Integer, String>(producerFactory()) {
    //
    //        @Override
    //        public ListenableFuture<SendResult<String, String>> send(String topic, String data) {
    //            return super.send(topic, partitionForData(data), keyForData(data), data);
    //        }
    //
    //        //...
    //
    //    };
    //}


    //
    // If the listener method returns Message<?> or Collection<Message<?>>,
    // the listener method is responsible for setting up the message headers for the reply.
    // For example, when handling a request from a ReplyingKafkaTemplate, you might do the following:
    @KafkaListener(id = "messageReturned", topics = "someTopic")
    public Message<?> listen(String in, @Header(KafkaHeaders.REPLY_TOPIC) byte[] replyTo,
                             @Header(KafkaHeaders.CORRELATION_ID) byte[] correlation) {
        return MessageBuilder.withPayload(in.toUpperCase())
                .setHeader(KafkaHeaders.TOPIC, replyTo)
                .setHeader(KafkaHeaders.MESSAGE_KEY, 42)
                .setHeader(KafkaHeaders.CORRELATION_ID, correlation)
                .setHeader("someOtherHeader", "someValue")
                .build();
    }


    // When using request/reply semantics, the target partition can be requested by the sender.


    // You can annotate a @KafkaListener method with @SendTo even if no result is returned.
    // This is to allow the configuration of an errorHandler that can forward information
    // about a failed message delivery to some topic.
    // The following example shows how to do so:
    @KafkaListener(id = "voidListenerWithReplyingErrorHandler", topics = "someTopic", errorHandler = "voidSendToErrorHandler")
    @SendTo("failures")
    public void voidListenerWithReplyingErrorHandler(String in) {
        throw new RuntimeException("fail");
    }

    @Bean
    public KafkaListenerErrorHandler voidSendToErrorHandler() {
        return (m, e) -> {
            return null;//... // some information about the failure and input data
        };
    }
    // 	If a listener method returns an Iterable, by default a record for each element as the value is sent.
    // 	Starting with version 2.3.5, set the splitIterables property on @KafkaListener to false
    // 	and the entire result will be sent as the value of a single ProducerRecord.
    // 	This requires a suitable serializer in the reply template’s producer configuration.
    // 	However, if the reply is Iterable<Message<?>> the property is ignored and each message is sent separately.
}
