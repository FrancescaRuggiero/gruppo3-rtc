package unisannio.assd.gruppo3.RTC.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DayAheadScheduling {

    @JsonProperty("giorno")
    String day;
    @JsonProperty("consumatori")
    ArrayList<DeviceSchedule> deviceSchedules;

    public boolean isDeviceScheduled(String idDevice, Timestamp timestamp) {
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        int hour = localDateTime.getHour();

        for(DeviceSchedule deviceSchedule: deviceSchedules)
            if(deviceSchedule.deviceId.equals(idDevice))
                return deviceSchedule.isDeviceScheduled(hour);

        return false;
    }


    public String toString() {
        return "{day:" + day + ", deviceSchedules:" + deviceSchedules.toString() + "}";
    }
}