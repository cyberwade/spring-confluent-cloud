package org.example.demo;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class KafkaEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Faker faker;

    @EventListener(ApplicationStartedEvent.class)
    public void produce() {
        var interval = Flux.interval(Duration.ofSeconds(1));
        var fakeMessages = Flux.fromStream(
                Stream.generate(() -> new ProducerRecord<>(
                        KafkaTopics.SAMPLE_EVENTS,
                        faker.hobbit().location(),
                        faker.hobbit().thorinsCompany())));

        Flux.zip(interval, fakeMessages)
                .map(record -> kafkaTemplate.send(record.getT2()))
                .blockLast();
    }
}
