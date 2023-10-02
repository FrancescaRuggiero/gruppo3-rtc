package unisannio.assd.gruppo3.RTC.controller;

import unisannio.assd.gruppo3.RTC.model.DAAvailableEnergy;

public class DAAvailableEnergyController {
	private DAAvailableEnergy dayAheadAvailableEnergy;
    private static DAAvailableEnergyController instance = null;

    private DAAvailableEnergyController() {}

    public static DAAvailableEnergyController getInstance() {
            if (instance == null) {
                    instance = new DAAvailableEnergyController();
            }
            return instance;
    }

    public void updateDAAvailableEnergy(DAAvailableEnergy daae) {
            this.dayAheadAvailableEnergy = daae;
    }

    public DAAvailableEnergy getDAAvailableEnergy() {
            return dayAheadAvailableEnergy;
    }
}
