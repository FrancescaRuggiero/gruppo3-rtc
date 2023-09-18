package unisannio.assd.gruppo3.RTC.model;
//Classe DAAvailableEnergy
//Quantit� di energia disponibile per il giorno x nelle corrispettive fasce orarie
//Letto dal topic DA_gen_data

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DAAvailableEnergy {
	private static final int ORE = 24;
    @JsonProperty("giorno")
	private String giorno; //Gestire eccezioni sulla data
    @JsonProperty("PUN")
	private double[] produzione = new double[ORE];
	
	/*public DAAvailableEnergy() {}
	public DAAvailableEnergy(LocalDate g, double[] prod) {giorno = g; produzione = prod;}*/
	
	public String getGiorno() {
		return giorno;
	}
	public void setGiorno(String giorno) {
		this.giorno = giorno;
	}
	public double[] getProduzione() {
		return produzione;
	}
	public void setProduzione(double[] produzione) {
		this.produzione = produzione;
	} 
	
	public boolean equals(DAAvailableEnergy c) {
		if(giorno.compareTo(c.getGiorno()) == 0) return true;
		else return false;
	}
	
		
}
