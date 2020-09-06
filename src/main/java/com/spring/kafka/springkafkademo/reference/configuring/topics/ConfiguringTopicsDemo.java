package com.spring.kafka.springkafkademo.reference.configuring.topics;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConfiguringTopicsDemo {

    /**
     * 	When using Spring Boot, a KafkaAdmin bean is automatically registered so you only need the NewTopic @Bean s.
     *
     * 	By default, if the broker is not available, a message is logged, but the context continues to load.
     * 	You can programmatically invoke the admin’s initialize() method to try again later.
     * 	If you wish this condition to be considered fatal, set the admin’s fatalIfBrokerNotAvailable property to true.
     * 	The context then fails to initialize.
     *
     * 	If the broker supports it (1.0.0 or higher), the admin increases the number of partitions if it is found that
     * 	an existing topic has fewer partitions than the NewTopic.numPartitions.
     *
     * 	For more advanced features, you can use the AdminClient directly. The following example shows how to do so:
     *
     *  <pre> {@code
     *  @Autowired
     *  private KafkaAdmin admin;
     *     ...
     *     AdminClient client = AdminClient.create(admin.getConfigurationProperties());
     *     ...
     *     client.close();
     *  }}</pre>
     */
    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        KafkaAdmin kafkaAdmin = new KafkaAdmin(configs);

        kafkaAdmin.initialize();
        kafkaAdmin.setFatalIfBrokerNotAvailable(true);

        return kafkaAdmin;
    }

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("thing1")
                .partitions(10)
                .replicas(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("thing2")
                .partitions(10)
                .replicas(3)
                .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
                .build();
    }

    @Bean
    public NewTopic topic3() {
        return TopicBuilder.name("thing3")
                .assignReplicas(0, Arrays.asList(0, 1))
                .assignReplicas(1, Arrays.asList(1, 2))
                .assignReplicas(2, Arrays.asList(2, 0))
                .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
                .build();
    }
}
