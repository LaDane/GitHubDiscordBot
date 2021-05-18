package Core;

import javax.security.auth.login.LoginException;
import Bot.*;
import BotLoop.BotLoop;

public class Main {

    public static void main(String[] args) throws LoginException, InterruptedException {
        Bot.run();
        try {Config.jda.awaitReady();} catch (Exception e) {System.out.println("Bot not ready");}
        BotLoop.startBotLoop();
    }
}































/* Tests */

// Load data from json
//        Config.app.deserializeAppSimple();
//        Config.allChannels.deserializeAllChannelsSimple();
//        Config.members.deserializeMembersSimple();
//        Config.discordRoles.deserializeDiscordRolesSimple();      // were not using roles
//        Config.botLogs.deserializeBotLogsSimple();
//        Config.pLangs.deserializePLangsSimple();
//        Config.botMsg.deserializeBotLogsSimple();

//        SetupChannels.setupChannels();
//        SetupRoles.setupRoles();                                  // were not using roles
//        SetupMessages.setupMessages();

//        System.out.println(PieChart.pieChart());

/* Change text channel name */
//        BotChannel testChannel = new BotChannel("840211735100653608", "log");
//        EditChannelName.editChannelName(testChannel, "change-test");


/* Add member to members.json */
//        Member m1 = new Member("TEST FEST", "https://github.com/tsukani", "https://avatars.githubusercontent.com/u/34612305?v=4", "https://api.github.com/repos/Tsukani/CPHexercises/commits/c8d7179ef88b28d6ed67b22e0d29eb997a5e72f4", "338099136006062082", 0, new ArrayList<Integer>(0));
//        Member m2 = new Member("NUMBER TWO0o0", "https://github.com/tsukani", "https://avatars.githubusercontent.com/u/34612305?v=4", "https://api.github.com/repos/Tsukani/CPHexercises/commits/c8d7179ef88b28d6ed67b22e0d29eb997a5e72f4", "338099136006062082", 0, new ArrayList<Integer>(0));
//        Config.members.addToMembers(m1);
//        Config.members.addToMembers(m2);
//        Config.members.serializeDatamatikerClassSimple();
//        System.out.println(Config.members.getMembers().get(0).getMemberGithubName());


/* Load members from members.json*/
//        Config.members.deserializeMembersSimple();
//        System.out.println(Config.members.getMembers().get(0).getMemberGithubName());
//        System.out.println(Config.members.getMembers().get(1).getMemberGithubName());


/* Test api */
//        System.out.println(API.request("https://api.github.com/users/LaDane"));
//        System.out.println(API.request("https://api.github.com/search/commits?q=author:LaDane&sort=author-date&order=desc&page=1", "application/vnd.github.cloak-preview"));
//        System.out.println(API.request("https://api.github.com/repos/:ladane/:repo/commits?per_page=1"));


//        Auth test = new Auth();
//        Auth.authRequest("https://api.github.com/users/ladane");