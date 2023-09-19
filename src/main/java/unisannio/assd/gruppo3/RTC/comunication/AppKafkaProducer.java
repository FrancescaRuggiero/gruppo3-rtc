package unisannio.assd.gruppo3.RTC.comunication;

import unisannio.assd.gruppo3.RTC.model.Command;
import unisannio.assd.gruppo3.RTC.model.ConsumptionWarning;
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
    private KafkaProducer<String, Command> commandKafkaProducer;
    public AppKafkaProducer(String applicationId, String brokers)  {
        // Set properties used to configure the producer
        Properties props = new Properties();
        Properties propc = new Properties();
        // Set the brokers (bootstrap servers)
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        propc.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        propc.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        // Set how to serialize key/value pairs
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        propc.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "unisannio.assd.gruppo3.RTC.utils.ConsumptionWarningSerializer");
        propc.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "unisannio.assd.gruppo3.RTC.utils.CommandSerializer");

        // create the configured producer
        consumptionWarningKafkaProducer = new KafkaProducer<>(props);
        commandKafkaProducer = new KafkaProducer<>(propc);
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

        }
    }
    public void produceCommand(Command comm) throws IOException {
        try {
            String topic = "CONTROL_COMMANDS";
            commandKafkaProducer.send(new ProducerRecord<String, Command>(
                    topic,
                    new Random().nextInt(10000)+"",
                    comm
            ));
            commandKafkaProducer.flush();
            System.out.println("Command sent: " + comm.toString() + "to topic: " + topic);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());

        }
    }
}