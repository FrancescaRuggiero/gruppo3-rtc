package dto;


import org.bson.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadData {
    public static enum DeviceType {
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
    private double value;
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

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double d) {
		this.value = d;
	}

	public long getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(long intervalTime) {
		this.intervalTime = intervalTime;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

    public Document convertDoc() {
		return new Document("deviceId",deviceId).append("timestamp",timestamp).append("type",deviceType).append("value",value).append("intervalTime",intervalTime);
    	
    }
}