package com.berdanakbulut.stream.compute.socket;

import com.berdanakbulut.services.config.AppConfig;
import com.berdanakbulut.services.models.ClickEvent;
import com.berdanakbulut.services.serializers.ClickEventDeserializationSchema;
import com.berdanakbulut.stream.processors.ClickProcessor;
import com.berdanakbulut.stream.utils.EnvironmentUtils;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.pulsar.source.PulsarSource;
import org.apache.flink.connector.pulsar.source.enumerator.cursor.StartCursor;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.pulsar.client.api.SubscriptionType;

import java.util.Properties;

public class ClickStream {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = EnvironmentUtils.initEnvWithWebUI(false);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "kafka:9092");
        properties.setProperty("zookeeper.connect", "zookeeper:2181");
        properties.setProperty("group.id", "kafka-flink-consumer");

        // Example Kafka Source
        KafkaSource<ClickEvent> source = KafkaSource.<ClickEvent>builder()
                .setProperties(properties)
                .setTopics(AppConfig.KAFKA_CLICK_TOPIC)
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new ClickEventDeserializationSchema())
                .build();

        DataStream<ClickEvent> clickEventDataStream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");

        clickEventDataStream.
                process(new ClickProcessor())
                .returns(TypeInformation.of(ClickEvent.class))
                .name("click pr")
                .uid("click pr");

        // Example Pulsar Source
        PulsarSource<ClickEvent> clickEventPulsarSource = EnvironmentUtils.initPulsarSource(AppConfig.CLICK_TOPIC,
                "flink-click-consumer",
                SubscriptionType.Exclusive,
                StartCursor.earliest(),
                ClickEvent.class);

        DataStream<ClickEvent> clickEventDataStreamPulsar = env.fromSource(clickEventPulsarSource, WatermarkStrategy.noWatermarks(), "Pulsar Click Source")
                .name("pulsarClickSource")
                .uid("pulsarClickSource");

        clickEventDataStream
                .print()
                .uid("print-click-event")
                .name("print-click-event");

        env.execute();
    }
}
