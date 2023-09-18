package unisannio.assd.gruppo3.RTC.controller;
import unisannio.assd.gruppo3.RTC.model.*;
import unisannio.assd.gruppo3.RTC.utils.ConnectionDB;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.bson.Document;

import com.mongodb.client.MongoCollection;




public class LoadController {
	
    private static LoadController instance = null;
    private MongoCollection<Document> collection = ConnectionDB.getInstance().getDatabase().getCollection("LoadDatas");
    
    public LoadController() {}

    public static LoadController getInstance() {
        if (instance == null) {
            instance = new LoadController();
        }
        return instance;
    }
    //public void controlLoadData(LoadData loadData) {
    public void controlLoadData(DAAvailableEnergy ae, Tariff tar, LoadData loadData) {
        DayAheadScheduling dayAheadScheduling = DASController.getInstance().getDayAheadScheduling();
        if(dayAheadScheduling != null) {
            // check if load data is coherent with day ahead scheduling
            Timestamp timestamp = new Timestamp(Long.valueOf(loadData.getTimestamp()));
            boolean scheduled = dayAheadScheduling.isDeviceScheduled(loadData.getDeviceId(), timestamp);
            String id = loadData.getDeviceId();
            if (!scheduled && !(id.substring(14,15).equalsIgnoreCase("I"))) {
                System.out.println("call command unisannio.assd.gruppo3.RTC.controller to disable device with id: " + loadData.getDeviceId());
                System.out.println("compute and send notification warnings");
                NotifyController.getInstance().sendNotification(ae,tar,loadData);
            }else {
                Command c = new Command();
                CommandsController.getInstance().sendCommand(c);
                System.out.println("Dispositivo interrompibile");
            }
        }else System.out.println("NON PRENDO DAS");
    }

	public void updateActiveLoadData(LoadData loadData) {//Inserire eventuale controllo di due documenti uguali
        
        updateCollection();
        
        if(loadData.getValue() != 0 && (loadData.getDeviceType().equals(LoadData.DeviceType.load))) {//Se il dispositivo consuma inseriscilo 
			collection.insertOne(loadData.convertDoc());
			System.out.println("Oggetto Load Data è stato inserito");
		}
        
	}

	private void updateCollection() {//Se in db e non corrisponde a fascia oraria, elimina
		for(Document d : collection.find()){
        	Timestamp timestamp = new Timestamp(Long.valueOf((String) d.get("timestamp")));
    		LocalDateTime t =  timestamp.toLocalDateTime();
    		System.out.println("orario ora: "+LocalTime.now()+"orario timestamp: "+t.getHour());
    		if(t.getHour()!= LocalTime.now().getHour()) collection.deleteOne(d);
    		System.out.println("Collezione aggiornata");
        }		
	}

	/*private boolean isPresent(LoadData loadData) {//Controlla se LoadData è in db
		Document d = findLD(loadData);
		if(d != null) return true;
		return false;
    }
	
	private Document findLD(LoadData loadData) {//Ottieni il documento di LoadData
		FindIterable<Document> docs = collection.find(eq("deviceId",loadData.getDeviceId()));
		for(Document d: docs) if(d.get("timestamp").equals(loadData.getTimestamp())) return d;
		return null;
	}
*/
}
