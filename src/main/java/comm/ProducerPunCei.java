
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerPunCei {
	private KafkaProducer<String, String> producer;
	
	public ProducerPunCei(String brokers)  {

        // Set properties used to configure the producer
        Properties props = new Properties();
        // Set the brokers (bootstrap servers)
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        // Set how to serialize key/value pairs
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
       
        // create the configured producer
        producer = new KafkaProducer<String, String>(props);
        
	}
	public void produce(String topicName)throws IOException {
       //hard coded tariffs, should have read them from a csv :')
        String[] PUN = new String[] {
                "195.90",
                "191.09",
                "187.95",
                "187.82",
                "187.74",
                "187.74",
                "187.22",
                "187.82",
                "186.47",
                "186.47",
                "177.25",
                "168.50",
                "173.00",
                "165.89",
                "179.73",
                "188.06",
                "193.67",
                "213.36",
                "229.99",
                "243.42",
                "235.00",
                "203.25",
                "190.85",
                "187.28"
        };
        String[] CEI = new String[] {
        	"22500,388",
        	"21103,282",
        	"20277,135",
        	"19618,601",
        	"19414,432",
        	"19750,73",
        	"20401,797",
        	"21408,557",
        	"21517,472",
        	"23241,292",
        	"24176,315",
        	"24775,971",
        	"24852,886",
        	"23644,926",
        	"22908,65",
        	"23827,466",
        	"24641,759",
        	"27043,288",
        	"28505,515",
        	"28265,241",
        	"28435,106",
        	"26857,221",
        	"24154,725",
        	"22288,132"
        };
     // Produce a Json of a 'Tariffs' instance
        Gson gson = new GsonBuilder().create();
        Date data = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Tariffs toSend = new Tariffs(data, PUN, CEI);
        String message = gson.toJson(toSend);
        System.out.println(toSend.getGiorno());
        
            try {
            	// Send the JSON to the topic
                producer.send(new ProducerRecord<String, String>(topicName, message)).get();
            } catch (Exception ex) {
                System.out.print(ex.getMessage());
                throw new IOException(ex.toString());
            }  
        }
    
	
	public static void main(String args[]) throws IOException {
		ProducerPunCei p = new ProducerPunCei("172.31.3.218:31200");
		p.produce("TARIFF");//topic che rappresenta lo stream di input
	}

}