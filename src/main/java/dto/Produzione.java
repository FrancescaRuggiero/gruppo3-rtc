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
public class Produzione {
    private static final int ORE = 24;
    @JsonProperty("values")
    private double[] values = new double[ORE];
    public double[] getValues() {
        return values;
    }
    public void setValues(double[] values) {
        this.values = values;
    }

    public String toString(){
        String str = "Produzione: \n";
        for(int i=0; i<ORE; i++) {str = str+i+": "+values[i]+" KWh \n";}
        str = str+"] \n";
        return str;
    }
}
