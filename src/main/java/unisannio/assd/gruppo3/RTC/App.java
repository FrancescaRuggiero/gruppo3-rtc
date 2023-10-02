package unisannio.assd.gruppo3.RTC;

//{"id_disp":"AAA-1834-LOAD-S-2","timestamp":"1694797367000","disp_type":"load","value":30000, "intervalTime":10000}
import unisannio.assd.gruppo3.RTC.comunication.AppKafkaConsumer;
import unisannio.assd.gruppo3.RTC.comunication.AppKafkaProducer;
import unisannio.assd.gruppo3.RTC.utils.ConnectionDB;

import java.util.Map;

public class App {
    //"172.31.3.218:31200"
    //"localhost:9092"

    static Map<String, String> env = System.getenv();

    private static String APP_ID = env.get("APP_ID");
    private static String BROKERS = env.get("BROKERS");
    private static String MONGO_URI = env.get("MONGO_URI");
    public static void main(String[] args) throws InterruptedException {
        ConnectionDB.getInstance().connectToDB(MONGO_URI);
        AppKafkaProducer.getInstance();
        AppKafkaConsumer appConsumer = new AppKafkaConsumer(APP_ID, BROKERS);
        appConsumer.start();
    }
}
