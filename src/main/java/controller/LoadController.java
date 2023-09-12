package controller;

import dto.*;

import java.sql.Timestamp;

public class LoadController {

    private static LoadController instance = null;

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
                System.out.println("call command controller to disable device with id: " + loadData.getDeviceId());
                System.out.println("compute and send notification warnings");
                NotifyController.getInstance().sendNotification(ae,tar,loadData);
            }else {
                Command c = new Command();
                CommandsController.getInstance().sendCommand(c);
                System.out.println("Dispositivo interrompibile");
            }
        }
    }

}
