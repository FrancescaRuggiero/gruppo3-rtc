package unisannio.assd.gruppo3.RTC.controller;

import unisannio.assd.gruppo3.RTC.model.DayAheadScheduling;

public class DASController {
        private DayAheadScheduling dayAheadScheduling;
        private static DASController instance = null;

        private DASController() {}

        public static DASController getInstance() {
                if (instance == null) {
                        instance = new DASController();
                }
                return instance;
        }

        public void updateDayAheadScheduling(DayAheadScheduling dayAheadScheduling) {
                this.dayAheadScheduling = dayAheadScheduling;
        }

        public DayAheadScheduling getDayAheadScheduling() {
                return dayAheadScheduling;
        }
}
