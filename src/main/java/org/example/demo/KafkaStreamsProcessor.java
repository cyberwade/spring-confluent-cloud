package org.example.demo;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class KafkaStreamsProcessor {
    public KafkaStreamsProcessor(StreamsBuilder streamsBuilder) {
        var stringSerde = Serdes.String();
        var textLines = streamsBuilder.stream(KafkaTopics.SAMPLE_EVENTS, Consumed.with(stringSerde, stringSerde));

        textLines
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
                .groupBy((key, value) -> value, Grouped.with(stringSerde, stringSerde))
                .count()
                .toStream()
                .foreach((key, value) -> {
                    System.out.printf("Key: %s , Value: %s\n", key, value);
                });
    }
}
