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
    private double revenues;
    private int consumption;
    private double cost;
    private double loss;


	public int getConsumption() {
        return this.consumption;
    }

    public double getLoss() {
        return this.loss;
    }
}
