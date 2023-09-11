package controller;

import comm.AppKafkaProducer;
import dto.ConsumptionWarning;
import dto.EconomicStatus;
import dto.LoadData;

import java.io.IOException;

public class NotifyController {
    private static NotifyController instance = null;

    public NotifyController() {}

    public static NotifyController getInstance() {
        if (instance == null) {
            instance = new NotifyController();
        }
        return instance;
    }
    public void sendNotification(LoadData loadData) {
        AppKafkaProducer producer = AppKafkaProducer.getInstance("app-4", "172.31.3.218:31200");
        EconomicStatus economicStatus = computeEconomicStatus();
        String message = "Lo scheduling della REC è stato violato: il dispositivo con id " +
                loadData.getDeviceId() + " è acceso. Il sistema di controllo interverrà" +
                "per spegnerlo qualora sia interrompibile.";

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

    private EconomicStatus computeEconomicStatus() {
        // todo: temp solution -> insert mock value
        return new EconomicStatus(4,2,3,2);
        //return null; // todo
    }
}
