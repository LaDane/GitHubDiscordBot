package Core;

import javax.security.auth.login.LoginException;

import Bot.*;
import BotChannel.*;


public class Main {

    public static void main(String[] args) throws LoginException, InterruptedException {
        Bot.run();

        BotChannel testChannel = new BotChannel("840211735100653608", "log");
        EditChannelName.editChannelName(testChannel, "change-test");
    }
}
