package comm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.DASController;
import controller.LoadController;
import dto.DayAheadScheduling;
import dto.LoadData;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import java.util.Properties;

public class AppKafkaConsumer {
    Properties props;
    StreamsBuilder builder;
    KStream<String, String> HCLoadSource;
    KStream<String, String> DASSource;
    KafkaStreams streams;

    public AppKafkaConsumer(String applicationId, String brokers) {
        props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    }

    public void start() throws InterruptedException {
        builder = new StreamsBuilder();
        HCLoadSource = builder.stream("HC_LOAD");
        DASSource = builder.stream("DA_SCHEDULING");

        System.out.println("start!");

        DASSource.foreach((key, value) -> {
            System.out.println("DAS topic new message");
            DayAheadScheduling dayAheadScheduling = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                dayAheadScheduling = mapper.readValue(value, DayAheadScheduling.class);
            } catch (JsonProcessingException e) {
                System.err.println("An error occurred during deserialization: " + e.getMessage());
                throw new RuntimeException(e);
            }
            System.out.println(dayAheadScheduling);
            DASController.getInstance().updateDayAheadScheduling(dayAheadScheduling);
        });

        HCLoadSource.foreach((key, value) -> {
            System.out.println("load data topic new message");
            LoadData loadData = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                loadData = mapper.readValue(value, LoadData.class);
            } catch (JsonProcessingException e) {
                System.err.println("An error occurred during deserialization: " + e.getMessage());
                throw new RuntimeException(e);
            }
            System.out.println(loadData);
            LoadController.getInstance().controlLoadData(loadData);
        });

        streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
}