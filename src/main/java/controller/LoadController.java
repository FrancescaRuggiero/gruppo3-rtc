package controller;

import dto.DayAheadScheduling;
import dto.LoadData;

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

    public void controlLoadData(LoadData loadData) {
        DayAheadScheduling dayAheadScheduling = DASController.getInstance().getDayAheadScheduling();

        if(dayAheadScheduling != null) {
            // check if load data is coherent with day ahead scheduling
            Timestamp timestamp = new Timestamp(Long.valueOf(loadData.getTimestamp()));
            boolean scheduled = dayAheadScheduling.isDeviceScheduled(loadData.getDeviceId(), timestamp);
            if (!scheduled) {
                System.out.println("call command controller to disable device with id: " + loadData.getDeviceId());
                // TODO
                System.out.println("compute and send notification warnings");
                NotifyController.getInstance().sendNotification(loadData);
            }
        }
    }

}
