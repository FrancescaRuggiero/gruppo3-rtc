package unisannio.assd.gruppo3.RTC.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Command {
    public static enum Commands {id_comm0, id_comm1};  //Possibili id: id di spegnimento, id di accensione
    public static enum Instructions {Spegnimento, Accensione};  //Possibili istruzioni: Spegnimento o accensione
    private String id_dispositivo; // Id del dispositivo da interrompere
    private Commands id_comando; // Id del comando di interruzione
    private Timestamp timestamp; //Timestamp del comando
    private Instructions value; //Valore del comando

    public String getId_dispositivo() {
        return id_dispositivo;
    }
    public void setId_dispositivo(String id_dispositivo) {
        this.id_dispositivo = id_dispositivo;
    }
    public String getId_comando() {
        return id_comando.toString();
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    public String getValue() {
        return value.toString();
    }
}
