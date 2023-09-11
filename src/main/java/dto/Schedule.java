package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Schedule {

    @JsonProperty("values")
    ArrayList<Boolean> values;

    public String toString() {
        String scheduleToString = "schedule: ";
        for (Boolean value: values) {
            scheduleToString += value.toString() + ", ";
        }
        return scheduleToString;
    }
}