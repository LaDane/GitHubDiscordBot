package Core;


import Bot.DiscordBot;
import BotMessage.BotMsg;
import PLang.PLangs;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import java.util.ArrayList;

import BotChannel.BotChannel;
import BotChannel.AllChannels;
import Member.Member;
import Member.Members;
import Role.DiscordRole;
import Role.DiscordRoles;
import BotLog.BotLogs;

public class Config {
    public static JDA jda;
    public static JDABuilder builder;
    public static Guild guild;
    public static DiscordBot bot = null;
    public static AllChannels allChannels = new AllChannels(new ArrayList<BotChannel>());
    public static Members members = new Members(new ArrayList<Member>());
    public static DiscordRoles discordRoles = new DiscordRoles(new ArrayList<DiscordRole>());
    public static BotLogs botLogs = new BotLogs();
//    public static App app = new App("");
    public static PLangs pLangs = new PLangs();
    public static BotMsg botMsg = new BotMsg();

//    public static void config() {
//        allChannels = new AllChannels(new ArrayList<BotChannel>());
//        members = new Members(new ArrayList<Member>());
//        discordRoles = new DiscordRoles(new ArrayList<DiscordRole>());
//        botLogs = new BotLogs(new ArrayList<BotLog>());
//    }
}
