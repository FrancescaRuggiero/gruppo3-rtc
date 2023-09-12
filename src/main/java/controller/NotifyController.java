package controller;

import comm.AppKafkaProducer;
import dto.ConsumptionWarning;
import dto.EconomicStatus;
import dto.LoadData;
import dto.Tariff;
import dto.DAAvailableEnergy;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

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
    	//DUBBIO: IO BASO LA MIA MISURA DEI COSTI SU TUTTE LE MISURE RICEVUTE IN QUELLA FASCIA ORARIA FINO ALL'ISTANTE DI VIOLAZIONE DEL PIANO, MA EFFETTIVAMENTE NOI NE CATTURIAMO UNA ALLA VOLTA?QUINDI NON POSSO FARE L'ARRAY?
    	//arraylist di eventi di load data così usiamo più misure per valutare
		//se carico più dispositivi alla volta e ogni dispositivo consuma tot, il confronto avviene su 1 e non su tutti
		//lista di dispositivi attivi
		ArrayList<LoadData> ld = new ArrayList<LoadData>();
    	ld.add(loadData);
    	AppKafkaProducer producer = AppKafkaProducer.getInstance("pippo15", "172.31.3.218:31200");
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
    private EconomicStatus computeEconomicStatus(DAAvailableEnergy e, ArrayList<LoadData> em, LocalTime fasciaoraria, Tariff tar) {
    	int f = OFF_UNDERC;
    	double cenergy = 0;
		double avenergy = 0;
		double cost = 0;
		double rev = 0;
		double[] p = e.getProduzione();
		for(LoadData t: em) {
			cenergy = cenergy+(t.getValue()*kWhTOkWm);//fattore di conversione di quanti kWh spende in 10 min
			System.out.println(cenergy);
			avenergy = getAEUnit(p, fasciaoraria); //* fasciaoraria.getMinute()/min; //Ottieni l'energia disponibile fino a quell'istante
			System.out.println(avenergy);
			//cenergy - consumata
			//av
			if(cenergy>avenergy) {
				f=ON_OVERC;
				cost = getCosts(cenergy, avenergy, tar, fasciaoraria);
				rev = getRevenues(cenergy,avenergy,tar,fasciaoraria);
				}
		}
		return new EconomicStatus(rev,f,cost,rev-cost);
	}
    
private double getAEUnit(double[] p, LocalTime fasciaoraria) {return (p[fasciaoraria.getHour()+1]/FACTOR_UNIT); }
	
	//Calcolo l'energia consumata in più di quella stabilità e la moltiplico per il Prezzo Unico Nazionale per ottenere il costo del sovraconsumo
	private double getCosts(double cenergy, double aenergy, Tariff tar, LocalTime f) { 
		double diff = (cenergy-aenergy)/kWhTOWh; //Per ottenere l'energia in Wh
		double[] pun = tar.getPun();
		return diff*(pun[f.getHour()])/MWhTOWh;//Per ottenere il costo in Wh
	}
	
	private double getRevenues(double cenergy, double aenergy, Tariff tar, LocalTime f) { //Calcolo CEI
		double diff = (aenergy-cenergy)/kWhTOWh; //Per ottenere l'energia in Wh
		double[] cei = tar.getCei();
		return diff*(cei[f.getHour()])/MWhTOWh;//Per ottenere il costo in Wh
	}
	private String getMessage(EconomicStatus ec, String id) {
		String message = "Lo scheduling della REC è stato violato: il dispositivo con id " +id + " è acceso.";
		if(ec.getConsumption()== 1) message = message+"\n La perdita generata a causa della violazione è: "+ec.getLoss()+" euro/Wh.";
		else message = message+" E' presente ancora dell'energia disponibile: per ora ancora non c'è perdita di denaro.";
		return message;
	}
}
