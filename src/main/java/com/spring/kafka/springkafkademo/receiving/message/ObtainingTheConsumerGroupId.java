package com.spring.kafka.springkafkademo.receiving.message;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public class ObtainingTheConsumerGroupId {

    // When running the same listener code in multiple containers,
    // it may be useful to be able to determine which container
    // (identified by its group.id consumer property) that a record came from.
    //
    //You can call KafkaUtils.getConsumerGroupId() on the listener thread to do this.
    // Alternatively, you can access the group id in a method parameter.
    @KafkaListener(
            id = "bar",
            topicPattern = "${topicTwo:annotated2}"
            //exposeGroupId = "${always:true}"  // can not found this property
    )
    public void listener(@Payload String foo,
                         @Header(KafkaHeaders.GROUP_ID) String groupId) {
        //...
    }
    // This is available in record listeners and batch listeners that receive a List<?> of records.
    // It is not available in a batch listener that receives a ConsumerRecords<?, ?> argument.
    // Use the KafkaUtils mechanism in that case.

}
