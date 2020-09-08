package com.spring.kafka.springkafkademo.receiving.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Valid;
import javax.validation.constraints.Max;

public class KafkaListenerPayloadValidation {

    // Starting with version 2.2, it is now easier to add a Validator to validate @KafkaListener @Payload arguments.
    // Previously, you had to configure a custom DefaultMessageHandlerMethodFactory and add it to the registrar.
    // Now, you can add the validator to the registrar itself.
    // The following code shows how to do so:
    @Configuration
    @EnableKafka
    public class Config implements KafkaListenerConfigurer {

        // ...

        @Override
        public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
            //registrar.setValidator(new MyValidator());
        }
    }


    // 	When you use Spring Boot with the validation starter, a LocalValidatorFactoryBean is auto-configured,
    // 	as the following example shows:
    @Configuration
    @EnableKafka
    public class Config1 implements KafkaListenerConfigurer {

        @Autowired
        private LocalValidatorFactoryBean validator;
        //...

        @Override
        public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
            registrar.setValidator(this.validator);
        }
    }

    // The following examples show how to validate:
    public static class ValidatedClass {

        @Max(10)
        private int bar;

        public int getBar() {
            return this.bar;
        }

        public void setBar(int bar) {
            this.bar = bar;
        }

    }
    @KafkaListener(id="validated", topics = "annotated35", errorHandler = "validationErrorHandler",
            containerFactory = "kafkaJsonListenerContainerFactory")
    public void validatedListener(@Payload @Valid ValidatedClass val) {
        //...
    }

    @Bean
    public KafkaListenerErrorHandler validationErrorHandler() {
        return (m, e) -> {
            //...
            return null;
        };
    }
}
