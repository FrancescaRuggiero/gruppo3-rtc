import comm.AppKafkaConsumer;
import comm.AppKafkaProducer;

public class App {
    //"172.31.3.218:31200"
    //"localhost:9092"
    private static String APP_ID = "pippo15";
    private static String BROKERS = "172.31.3.218:31200";

    public static void main(String[] args) throws InterruptedException {
        AppKafkaProducer producer = new AppKafkaProducer(APP_ID, BROKERS);
        AppKafkaConsumer appConsumer = new AppKafkaConsumer(APP_ID, BROKERS);
        appConsumer.start();
    }
}
