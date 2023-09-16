package comm;

import dto.ConsumptionWarning;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.StreamsConfig;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class AppKafkaProducer {
    private KafkaProducer<String, ConsumptionWarning> consumptionWarningKafkaProducer;
    private static AppKafkaProducer instance;

    public AppKafkaProducer(String applicationId, String brokers)  {
        // Set properties used to configure the producer
        Properties props = new Properties();
        // Set the brokers (bootstrap servers)
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        // Set how to serialize key/value pairs
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "utils.ConsumptionWarningSerializer");

        // create the configured producer
        consumptionWarningKafkaProducer = new KafkaProducer<>(props);
    }

    public static AppKafkaProducer getInstance() {
        if (instance == null) {
            Map<String, String> env = System.getenv();
            String appId = env.get("APP_ID");
            String brokers = env.get("BROKERS");
            instance = new AppKafkaProducer(appId, brokers);
        }
        return instance;
    }



    public void produceConsumptionWarnings(ConsumptionWarning consumptionWarning) throws IOException {
        try {
            String topic = "CONSUMPTION_WARNING";
            consumptionWarningKafkaProducer.send(new ProducerRecord<String, ConsumptionWarning>(
                    topic,
                    new Random().nextInt(10000)+"",
                    consumptionWarning
            ));
            consumptionWarningKafkaProducer.flush();
            System.out.println("Consumption warning sent: " + consumptionWarning.toString() + "to topic: " + topic);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            throw new IOException(ex.toString());
        }
    }
}