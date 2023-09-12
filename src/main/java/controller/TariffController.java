package controller;

import dto.Tariff;

public class TariffController {
	private Tariff t;
    private static TariffController instance = null;

    private TariffController() {}

    public static TariffController getInstance() {
            if (instance == null) {
                    instance = new TariffController();
            }
            return instance;
    }

    public void updateTariff(Tariff t) {
            this.t = t;
    }

    public Tariff getTariff() {
            return t;
    }
}
