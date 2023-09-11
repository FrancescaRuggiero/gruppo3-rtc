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

    private String message;
    private int consumption; // sovraproduzione/sottoproduzione
    private int loss; // euro/Wh
}