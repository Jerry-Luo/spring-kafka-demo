package com.spring.kafka.springkafkademo.receiving.message;

public class RetryingDeliveries {

    // If your listener throws an exception, the default behavior is to invoke the Container Error Handlers,
    // if configured, or logged otherwise.
    //
    // NOTE: To retry deliveries, a convenient listener adapter RetryingMessageListenerAdapter is provided.

    // You can configure it with a RetryTemplate and RecoveryCallback<Void> -
    // see the spring-retry project for information about these components.
    // If a recovery callback is not provided, the exception is thrown to the container after retries are exhausted.
    // In that case, the ErrorHandler is invoked, if configured, or logged otherwise.

    // When you use @KafkaListener, you can set the RetryTemplate (and optionally recoveryCallback) on the container factory.
    // When you do so, the listener is wrapped in the appropriate retrying adapter.

    // The contents of the RetryContext passed into the RecoveryCallback depend on the type of listener.
    // The context always has a record attribute, which is the record for which the failure occurred.
    // If your listener is acknowledging or consumer aware, additional acknowledgment or consumer attributes are available.
    // For convenience, the RetryingMessageListenerAdapter provides static constants for these keys.
    // See its Javadoc for more information.
    //
    // A retry adapter is not provided for any of the batch message listeners,
    // because the framework has no knowledge of where in a batch the failure occurred.
    // If you need retry capabilities when you use a batch listener,
    // we recommend that you use a RetryTemplate within the listener itself.

}
