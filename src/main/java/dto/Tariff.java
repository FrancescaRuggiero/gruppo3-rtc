package dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

//Classe Tariff
//Per le tariffe di mercato
//Lette dall'apposito topic (Tariff_Market)
//Si raccolgono una al giorno
public class Tariff {
	private final int ORE = 24;
	@JsonProperty("giorno")
	private String giorno;
	@JsonProperty("PUN")
	private double[] pun =  new double[24]; //Per il PUN si usa il valore medio del sito GME-PREZZI, vale per ogni zona
	@JsonProperty("CEI")
	private double[] cei = new double[24]; //CEI del centrosud
	
	//public Tariff() {}

	/*public Tariff(LocalDate giorno, double[] pun, double[] cei) {
		this.giorno = giorno;
		this.pun = pun;
		this.cei = cei;
	}*/

	public String getGiorno() {
		return giorno;
	}

	public void setGiorno(String giorno) {
		this.giorno = giorno;
	}

	public double[] getPun() {
		return pun;
	}

	public void setPun(double[] pun) {
		this.pun = pun;
	}

	public double[] getCei() {
		return cei;
	}

	public void setCei(double[] cei) {
		this.cei = cei;
	}

	
	public String toString() {
		return "Tariff [giorno: "+giorno+", PUN:" + Arrays.toString(pun) + "(euro/Wh), CEI: " + Arrays.toString(cei) + "euro/Wh]";
	}
	


	
}
