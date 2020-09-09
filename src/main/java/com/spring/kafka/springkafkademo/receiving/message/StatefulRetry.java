package com.spring.kafka.springkafkademo.receiving.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

public class StatefulRetry {
    @Autowired
    DeadLetterPublishingRecoverer recoverer;

    //...
        //factory.setRetryTemplate(new RetryTemplate()); // 3 retries by default
        //factory.setStatefulRetry(true);
        //factory.setRecoveryCallback(context -> {
        //    recoverer.accept((ConsumerRecord<?, ?>) context.getAttribute("record"),
        //            (Exception) context.getLastThrowable());
        //    return null;
        //});
    //...

    @Bean
    public SeekToCurrentErrorHandler eh() {
        return new SeekToCurrentErrorHandler(new FixedBackOff(0L, 3L)); // at least 3
    }

    // However, see the note at the beginning of this section; you can avoid using the RetryTemplate altogether.

}
