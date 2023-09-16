package controller;

import comm.AppKafkaProducer;
import dto.ConsumptionWarning;
import dto.EconomicStatus;
import dto.LoadData;
import dto.Tariff;
import utils.ConnectionDB;
import dto.DAAvailableEnergy;

import java.io.IOException;

import java.time.LocalTime;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

public class NotifyController {
	private final int ON_OVERC = 1;//FLAG per Accensione o indicatore di overconsumption
	private final int OFF_UNDERC = 0; // FLAG per Spegnimento o indicatore di underconsumption
	private final int FACTOR_UNIT = 6; //Fattore per ottenere l'energia disponibile in 10 min
	private final int kWhTOWh = 1000; //Fattore di conversione da kWh a Wh
	private final int MWhTOWh = 1000000; //Fattore di conversione da MWh a Wh
	private final double kWhTOkWm = 0.1667; //Fattore di conversione da kWh a kWm
	private final int min = 10;


	private static NotifyController instance = null;

	public NotifyController() {}

	public static NotifyController getInstance() {
		if (instance == null) {
			instance = new NotifyController();
		}
		return instance;
	}
	public void sendNotification(DAAvailableEnergy ae, Tariff tar, LoadData loadData) {
		ArrayList<LoadData> ld = getLoadDatasFromDB();
		AppKafkaProducer producer = AppKafkaProducer.getInstance();
		EconomicStatus economicStatus = computeEconomicStatus(ae, ld, LocalTime.now(), tar);

		String message = getMessage(economicStatus, loadData.getDeviceId());

		ConsumptionWarning consumptionWarning = new ConsumptionWarning(
				message,
				economicStatus.getConsumption(),
				economicStatus.getLoss()
		);

		try {
			producer.produceConsumptionWarnings(consumptionWarning);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private ArrayList<LoadData> getLoadDatasFromDB() {
		ArrayList<LoadData> ld = new ArrayList<LoadData>();
		LoadData l = new LoadData();
		MongoCollection<Document> c = ConnectionDB.getInstance().getDatabase().getCollection("LoadDatas");
		for(Document d: c.find()) {
			l.setDeviceId((String) d.get("deviceId"));
			l.setTimestamp((String) d.get("timestamp"));
			l.setDeviceType(LoadData.DeviceType.load);
			l.setValue( Double.valueOf(d.get("value").toString()));
			l.setIntervalTime(Long.parseLong(d.get("intervalTime").toString()));
			ld.add(l);
			System.out.println("LOAD DATA CREATO: "+l+" e grandezza ld: "+ld.size());
		}
		return ld;
	}

	//Metodo per l'elaborazione dello stato economico
	private EconomicStatus computeEconomicStatus(DAAvailableEnergy e, ArrayList<LoadData> em, LocalTime fasciaoraria, Tariff tar) {
		int f = OFF_UNDERC;
		double cenergy = 0;
		double avenergy = 0;
		double cost = 0;
		double rev = 0;
		double[] p = e.getProduzione();
		for(LoadData t: em) {//Per ogni misura fai questi calcoli
			cenergy = cenergy+(t.getValue()*kWhTOkWm);//fattore di conversione di quanti kWh spende in 10 min
			avenergy = getAEUnit(p, fasciaoraria)* fasciaoraria.getMinute()/min; //Ottieni l'energia disponibile fino a quell'istante, al momento è settato su una unità
		}
		if(cenergy>avenergy) {
			f=ON_OVERC;
			cost = getCosts(cenergy, avenergy, tar, fasciaoraria);
			rev = getRevenues(cenergy,avenergy,tar,fasciaoraria);
		}
		return new EconomicStatus(rev,f,cost,rev-cost);
	}

	//Metodo per ottenere unità di energia disponibile
	private double getAEUnit(double[] p, LocalTime fasciaoraria) {return (p[fasciaoraria.getHour()+1]/FACTOR_UNIT); }

	//Metodo per ottenere il costo del sovraconsumo
	private double getCosts(double cenergy, double aenergy, Tariff tar, LocalTime f) {
		double diff = (cenergy-aenergy)/kWhTOWh; //Per ottenere l'energia in Wh
		double[] pun = tar.getPun();
		return diff*(pun[f.getHour()])/MWhTOWh;//Per ottenere il costo in Wh
	}

	//Metodo per ottenere il ricavo dell'energia immessa
	private double getRevenues(double cenergy, double aenergy, Tariff tar, LocalTime f) { //Calcolo CEI
		double diff = (aenergy-cenergy)/kWhTOWh; //Per ottenere l'energia in Wh
		double[] cei = tar.getCei();
		return diff*(cei[f.getHour()])/MWhTOWh;//Per ottenere il costo in Wh
	}

	//Metodo per elaborare il messsaggio della notifica di warning in caso di sovraconsumo o no
	private String getMessage(EconomicStatus ec, String id) {
		String message = "Lo scheduling della REC è stato violato: il dispositivo con id " +id + " è acceso.";
		if(ec.getConsumption()== 1) message = message+"\n La perdita generata a causa della violazione è: "+ec.getLoss()+" euro/Wh.";
		else message = message+" E' presente ancora dell'energia disponibile: per ora ancora non c'è perdita di denaro.";
		return message;
	}
}
