package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoadData {
    public enum DeviceType {

        load,
        generation
    }

    @JsonProperty("id_disp")
    private String deviceId;
    @JsonProperty("timestamp")
    private String timestamp; // milliseconds?
    @JsonProperty("disp_type")
    private DeviceType deviceType; // load or generation, meglio un enum type? TBD
    @JsonProperty("value")
    private int value;
    @JsonProperty("intervalTime")
    private long intervalTime;

    @Override
    public String toString(){
        return "{"
                +"deviceId:"+deviceId
                +", timestamp:"+timestamp
                +", deviceType:"+deviceType.name()
                +", value:"+value
                +", intervalTime:"+intervalTime
                +"}";
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

}