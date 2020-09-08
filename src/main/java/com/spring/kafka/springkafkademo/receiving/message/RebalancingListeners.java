package com.spring.kafka.springkafkademo.receiving.message;

public class RebalancingListeners {

    // ContainerProperties has a property called consumerRebalanceListener,
    // which takes an implementation of the Kafka client’s ConsumerRebalanceListener interface.
    // If this property is not provided, the container configures a logging listener
    // that logs rebalance events at the INFO level. The framework also adds a sub-interface ConsumerAwareRebalanceListener.
    // The following listing shows the ConsumerAwareRebalanceListener interface definition:
    //
    // public interface ConsumerAwareRebalanceListener extends ConsumerRebalanceListener {
    //
    //    void onPartitionsRevokedBeforeCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions);
    //
    //    void onPartitionsRevokedAfterCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions);
    //
    //    void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions);
    //
    //    void onPartitionsLost(Consumer<?, ?> consumer, Collection<TopicPartition> partitions);
    //
    //}

    // Notice that there are two callbacks when partitions are revoked.
    // The first is called immediately. The second is called after any pending offsets are committed.
    // This is useful if you wish to maintain offsets in some external repository, as the following example shows:
    //
    // containerProperties.setConsumerRebalanceListener(new ConsumerAwareRebalanceListener() {
    //
    //    @Override
    //    public void onPartitionsRevokedBeforeCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
    //        // acknowledge any pending Acknowledgments (if using manual acks)
    //    }
    //
    //    @Override
    //    public void onPartitionsRevokedAfterCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
    //        // ...
    //            store(consumer.position(partition));
    //        // ...
    //    }
    //
    //    @Override
    //    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
    //        // ...
    //            consumer.seek(partition, offsetTracker.getOffset() + 1);
    //        // ...
    //    }
    //});


}
