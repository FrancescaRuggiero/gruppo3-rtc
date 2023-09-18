package unisannio.assd.gruppo3.RTC.controller;

import unisannio.assd.gruppo3.RTC.model.Command;

public class CommandsController {
    private static CommandsController instance = null;

    public CommandsController() {}

    public static CommandsController getInstance() {
        if (instance == null) {
            instance = new CommandsController();
        }
        return instance;
    }
    public void sendCommand(Command command) {
        // todo
    }
}
