package com.spring.kafka.springkafkademo.sending.message.examples;

public class DefaultKafkaProducerFactoryDemo {

    // When not using Transactions, by default, the DefaultKafkaProducerFactory creates a singleton producer
    // used by all clients, as recommended in the KafkaProducer javadocs.
    // However, if you call flush() on the template, this can cause delays for other threads using the same producer.
    // Starting with version 2.3, the DefaultKafkaProducerFactory has a new property producerPerThread.
    // When set to true, the factory will create (and cache) a separate producer for each thread, to avoid this issue.

    // !!!	When producerPerThread is true, user code must call closeThreadBoundProducer() on the factory
    // when the producer is no longer needed. This will physically close the producer and remove it from the ThreadLocal.
    // Calling reset() or destroy() will not clean up these producers.

    // When creating a DefaultKafkaProducerFactory, key and/or value Serializer classes can be
    // picked up from configuration by calling the constructor that only takes in a Map of properties
    // (see example in Using KafkaTemplate), or Serializer instances may be passed to the
    // DefaultKafkaProducerFactory constructor (in which case all Producer s share the same instances).
    // Alternatively you can provide Supplier<Serializer> s (starting with version 2.3) that
    // will be used to obtain separate Serializer instances for each Producer:
    // @Bean
    // public ProducerFactory<Integer, CustomValue> producerFactory() {
    //    return new DefaultKafkaProducerFactory<>(producerConfigs(), null, () -> new CustomValueSerializer());
    // }
    //
    // @Bean
    // public KafkaTemplate<Integer, CustomValue> kafkaTemplate() {
    //    return new KafkaTemplate<Integer, CustomValue>(producerFactory());
    // }

}
