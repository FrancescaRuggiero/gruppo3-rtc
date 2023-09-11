package comm;

import org.apache.kafka.clients.producer.*;
import org.apache.log4j.BasicConfigurator;

import java.util.Properties;

public class AppKafkaProducerToREMOVE<S, C> {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        // Configura le proprietà del produttore Kafka
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "172.31.3.218:31200"); // Indirizzo del server Kafka
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Crea un produttore Kafka
        Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(properties);

        // Nome del topic a cui inviare i messaggi
        String topic = "RTC_notify"; // Sostituisci con il nome del tuo topic

        try {
            // Invia un messaggio al topic Kafka
            String message = "Il tuo messaggio Kafka"; // Contenuto del messaggio

            // Crea un record di produzione con chiave e valore
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);

            // Invia il record al topic
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("Messaggio inviato con successo al topic: " + metadata.topic());
                        System.out.println("Partizione: " + metadata.partition());
                        System.out.println("Offset: " + metadata.offset());
                    } else {
                        System.err.println("Errore nell'invio del messaggio: " + exception.getMessage());
                    }
                }
            });

            // Attendiamo un po' prima di chiudere il produttore (puoi rimuovere questo sleep se non necessario)
            Thread.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Chiudi il produttore Kafka
            producer.close();
        }
    }
}