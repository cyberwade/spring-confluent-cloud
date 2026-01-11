package org.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaEventConsumer {
    @KafkaListener(topics = KafkaTopics.SAMPLE_EVENTS)
    public void consume(ConsumerRecord<String, String> event, Acknowledgment acknowledgment) {
        log.info("Consumed event with key - {}, value - {}", event.key(), event.value());
        acknowledgment.acknowledge();
    }
}
