package org.example.demo;

import net.datafaker.Faker;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class SpringConfluentCloudConfiguration {
    @Bean
    public Faker faker() {
        return new Faker();
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(KafkaTopics.SAMPLE_EVENTS).partitions(12).replicas(6).build();
    }
}
