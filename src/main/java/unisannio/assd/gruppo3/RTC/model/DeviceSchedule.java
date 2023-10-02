package unisannio.assd.gruppo3.RTC.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeviceSchedule {

    @JsonProperty("giorno")
    String day;
    @JsonProperty("smartMeter")
    String deviceId;
    @JsonProperty("allocazione")
    Schedule schedule;

    public boolean isDeviceScheduled(int hour) {
        return schedule.values.get(hour);
    }

    public String toString() {
        return "{day: "+day+", deviceId: " + deviceId + ", " + schedule.toString() + "}";
    }
}