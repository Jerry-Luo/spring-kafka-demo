package com.spring.kafka.springkafkademo.quick.example.test.with.java.config;

import com.spring.kafka.springkafkademo.quick.sample.with.java.config.Listener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestWithJavaConfig {
    
    @Autowired
    private Listener listener;

    @Autowired
    private KafkaTemplate<Integer, String> template;

    @Test
    public void testSimple() throws Exception {
        template.send("annotated1", 0, "foo");
        template.flush();
        assertTrue(this.listener.latch1.await(10, TimeUnit.SECONDS));
    }
}
