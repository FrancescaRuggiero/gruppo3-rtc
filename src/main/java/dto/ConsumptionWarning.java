package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsumptionWarning {
	private static enum Tipi {OVERCONSUMPTION, UNDERCONSUMPTION}
    private String message;
    private Tipi consumption; // sovraproduzione/sottoproduzione
    private double loss; // euro/Wh

	public ConsumptionWarning(String message2, int consumption2, double d) {
		this.message = message2;
		if(consumption2 == 1) this.consumption = Tipi.OVERCONSUMPTION ; //Gestire un valore diverso da "Tipi"
		else this.consumption = Tipi.UNDERCONSUMPTION;
		this.loss = d;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Tipi getConsumption() {
		return consumption;
	}

	public void setConsumption(Tipi consumption) {
		this.consumption = consumption;
	}

	public double getLoss() {
		return loss;
	}

	public void setLoss(double loss) {
		this.loss = loss;
	}
	
	
}