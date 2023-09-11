package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EconomicStatus {
    // mock
    private int revenues;
    private int consumption;
    private int cost;
    private int loss;
    // todo

    public int getConsumption() {
        return this.consumption;
    }

    public int getLoss() {
        return this.loss;
    }
}
