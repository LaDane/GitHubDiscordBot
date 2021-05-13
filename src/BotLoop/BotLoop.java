package BotLoop;

import BotChannel.BotChannel;
import Core.Config;

public class BotLoop {

    private static int sleepTime = 10;          // sleep in seconds

    public static void startBotLoop() {
        BotChannel memberChannel = null;

        for (BotChannel channel : Config.allChannels.getAllChannels()) {
            if (channel.getChannelName().equals("members"))
                memberChannel = channel;
        }

        while(true) {
            try {
                Thread.sleep(sleepTime * 1000L);
                System.out.println("Paused for '"+ sleepTime +"' seconds");


            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
