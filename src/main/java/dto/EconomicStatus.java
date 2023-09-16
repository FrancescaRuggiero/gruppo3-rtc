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
    // todo

   /* public EconomicStatus(double i, int j, double k, double l) {
    	revenues = i;
    	consumption = j;
    	cost = k;
    	loss = l;
    }*/

	public int getConsumption() {
        return this.consumption;
    }

    public double getLoss() {
        return this.loss;
    }
}
