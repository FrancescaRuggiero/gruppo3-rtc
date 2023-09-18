package unisannio.assd.gruppo3.RTC.controller;

import unisannio.assd.gruppo3.RTC.comunication.AppKafkaProducer;
import unisannio.assd.gruppo3.RTC.model.Command;
import unisannio.assd.gruppo3.RTC.model.LoadData;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CommandsController {
    private static CommandsController instance = null;

    public CommandsController() {}

    public static CommandsController getInstance() {
        if (instance == null) {
            instance = new CommandsController();
        }
        return instance;
    }
    public void sendCommand(LoadData ld) {
        AppKafkaProducer producer = AppKafkaProducer.getInstance();
        Command.Commands idc = Command.Commands.id_comm0;
        Command.Instructions in = Command.Instructions.Spegnimento;
        if(ld.getValue() == 0) {idc = Command.Commands.id_comm1; in = Command.Instructions.Accensione;}
        Command c = new Command(ld.getDeviceId(),idc, Timestamp.valueOf(LocalDateTime.now()),in );
        try {
            producer.produceCommand(c);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
