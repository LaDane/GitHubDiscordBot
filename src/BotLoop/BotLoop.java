package BotLoop;

import BotChannel.BotChannel;
import Core.Config;
import Member.*;
import Chart.*;

public class BotLoop {

    private static int sleepTime = 180;          // sleep in seconds

    public static void startBotLoop() {
        BotChannel membersChannel = null;
        BotChannel leaderboardChannel = null;
        BotChannel commitsCommandsChannel = null;
        BotChannel graphsChannel = null;

        for (BotChannel channel : Config.allChannels.getAllChannels()) {
            if (channel.getChannelName().equals("members")) membersChannel = channel;
            if (channel.getChannelName().equals("leaderboard")) leaderboardChannel = channel;
            if (channel.getChannelName().equals("commits-commands")) commitsCommandsChannel = channel;
            if (channel.getChannelName().equals("graphs")) graphsChannel = channel;
        }

        while(true) {
            try {

                LoopMembers.loopMembers(membersChannel, commitsCommandsChannel);
                LoopMessages.loopMessages(leaderboardChannel, graphsChannel);

                System.out.println("Pausing for '"+ sleepTime +"' seconds");
                Thread.sleep(sleepTime * 1000L);
                System.out.println("Paused for '"+ sleepTime +"' seconds");



            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
