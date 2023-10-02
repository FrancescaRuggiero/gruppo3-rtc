package unisannio.assd.gruppo3.RTC.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsumptionWarning {
	//private static enum Tipi {OVERCONSUMPTION, UNDERCONSUMPTION}
    private String message;
    private int consumption; // sovraproduzione/sottoproduzione
    private double loss; // euro/Wh



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getConsumption() {
		return consumption;
	}

	public void setConsumption(int consumption) {
		this.consumption = consumption;
	}

	public double getLoss() {
		return loss;
	}

	public void setLoss(double loss) {
		this.loss = loss;
	}
	
	
}