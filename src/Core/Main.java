package Core;

import javax.security.auth.login.LoginException;

import Bot.*;
import Member.Members;
import Member.Member;
import Web.API;

import java.util.ArrayList;


public class Main {

    public static void main(String[] args) throws LoginException, InterruptedException {
        Bot.run();
        try {Config.jda.awaitReady();} catch (Exception e) {System.out.println("Bot not ready");}


//        SetupChannels.setupChannels();
//        SetupRoles.setupRoles();

//        BotLoop.startBotLoop();

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
        Config.members.deserializeDatamatikerClassSimple();
//        System.out.println(Config.members.getMembers().get(0).getMemberGithubName());
//        System.out.println(Config.members.getMembers().get(1).getMemberGithubName());


        /* Test api */
//        System.out.println(API.request("https://api.github.com/users/LaDane"));
//        System.out.println(API.request("https://api.github.com/search/commits?q=author:LaDane&sort=author-date&order=desc&page=1", "application/vnd.github.cloak-preview"));

    }
}
