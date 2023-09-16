package unisannio.assd.gruppo3.RTC;


//{"id_disp":"AAA-1834-LOAD-S-2","timestamp":"1694797367000","disp_type":"load","value":30000, "intervalTime":10000}
import comm.AppKafkaConsumer;
import comm.AppKafkaProducer;
import utils.ConnectionDB;
public class App {
    //"172.31.3.218:31200"
    //"localhost:9092"
    private static String APP_ID = "app2023";
    private static String BROKERS = "172.31.3.218:31200";

    public static void main(String[] args) throws InterruptedException {
    	ConnectionDB.getInstance().connectToDB();
    	AppKafkaProducer producer = new AppKafkaProducer(APP_ID, BROKERS);
        AppKafkaConsumer appConsumer = new AppKafkaConsumer(APP_ID, BROKERS);
        appConsumer.start();
    }
}
