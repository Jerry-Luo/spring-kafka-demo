package com.spring.kafka.springkafkademo.receiving.message;

public class CommittingOffsets {
    // The consumer poll() method returns one or more ConsumerRecords.
    // The MessageListener is called for each record.
    // The following lists describes the action taken by the container for each AckMode (when transactions are not being used):

    // RECORD:           Commit the offset when the listener returns after processing the record.
    // BATCH:            Commit the offset when all the records returned by the poll() have been processed.
    // TIME:             Commit the offset when all the records returned by the poll() have been processed,
    //                   as long as the ackTime since the last commit has been exceeded.
    //
    // COUNT:            Commit the offset when all the records returned by the poll() have been processed,
    //                   as long as ackCount records have been received since the last commit.
    //
    // COUNT_TIME:       Similar to TIME and COUNT, but the commit is performed if either condition is true.
    //
    // MANUAL:           The message listener is responsible to acknowledge() the Acknowledgment.
    //                   After that, the same semantics as BATCH are applied.
    //
    // MANUAL_IMMEDIATE: Commit the offset immediately when the Acknowledgment.acknowledge() method is called by the listener.
}
