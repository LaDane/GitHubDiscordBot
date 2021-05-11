package Message;

import Web.Request;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.*;
import com.google.gson.Gson;
import java.lang.reflect.Array;

import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.time.Instant;
import java.io.Reader;
import java.util.ArrayList;
import java.net.URL;

import Member.*;
import Bot.*;
import Core.*;


public class MessageListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(final GuildMessageReceivedEvent event) {

        String guildID = event.getGuild().getId();
        String channelID = event.getChannel().getId();
        String member = event.getMessage().getAuthor().getAsTag();
        String memberID = event.getAuthor().getId();
        String message = event.getMessage().getContentRaw();
        String messageID = event.getMessageId();
        String jumpURL = event.getMessage().getJumpUrl();

        // Don't continue if bot is message author
        if (memberID.equals(Bot.bot.getBotID()))
            return;

        //Reload data
        Config.members.deserializeDatamatikerClassSimple();



        String cmd = message.split(" ")[0].toLowerCase();

        EmbedBuilder embed = new EmbedBuilder();


        switch (cmd) {
            case "*help" -> {
                embed = new EmbedBuilder()
                        .setColor(Color.lightGray)
                        .setTitle("-----------------------------[ GitHub Stat Tracker ]-----------------------------")
                        .addField("[required] {optional}", "\u200B", false)
                        .addField("*adduser [url/name]", "Adds the following user to the tracking system and leaderboard", true)
                        .addField("*removeuser", "Removes your account from the tracking system and leaderboard (resets points)", true)
                        .addField("*recent {name} {days}", "Checks the users commit history within the specified amount of days (defaults to your own stats and 14 days; name required to use specific time period)", true)
                        .addField("*userstats {name}", "Checks various statistics for the user, including their point score (defaults to your stats)", true)
                        .addField("*leaderboard", "Shows a leaderboard with all the registered users and their point score", true)
                        .addField("*flip [points]", "Feelin' risky? Put your points on the line in a game of luck", true)
                        .addField("*shop", "Redeem rewards for your hard-earned points", true);
                Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
                embed.clear();
            }
            case "*adduser" -> {
                String name = "";
                try {
                    name = message.split(" ")[1];
                } catch (Exception e) {
                    embed = new EmbedBuilder()
                            .setColor(Color.red)
                            .setTitle("Missing user parameter");
                    Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
                    embed.clear();
                }
                for (Member d : Config.members.getMembers()) {
                    if (d.getMemberDiscordID().equals(memberID)) {
                        try {
                            embed = new EmbedBuilder()
                                    .setColor(Color.red)
                                    .setTitle("You already have an account (" + d.getMemberGithubName() + ")")
                                    .setThumbnail(d.getMemberGithubAvatarURL());
                        } catch (Exception e) {
                            e.printStackTrace();
                        };
                        Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
                        embed.clear();
                        return;
                    }
                }

                JsonObject json = Request.request("https://api.github.com/users/" + name, null);
                JsonObject json2 = Request.request("https://api.github.com/search/commits?q=author:" + name + "&sort=author-date&order=desc&page=1" , "application/vnd.github.cloak-preview");
                /*
                System.out.println("name: " + json.get("login").toString().replaceAll("\"", "") +
                        "\nurl: " + json.get("url").toString().replaceAll("\"", "") +
                        "\navatar_url: " + json.get("avatar_url").toString().replaceAll("\"", "") +
                        "\nlast_commit: " + json2.get("items").getAsJsonArray().get(0).getAsJsonObject().get("url").toString().replaceAll("\"", "") +
                        "\ndiscord: " + memberID +
                        "\npoints: " + "0" +
                        "\nitemsOwned: " + "[]");
                */

                //Save
                Config.members.addToMembers(new Member(
                        json.get("login").toString().replaceAll("\"", ""),
                        json.get("url").toString().replaceAll("\"", ""),
                        json.get("avatar_url").toString().replaceAll("\"", ""),
                        json2.get("items").getAsJsonArray().get(0).getAsJsonObject().get("url").toString().replaceAll("\"", ""),
                        memberID,
                        0,
                        new ArrayList<String>()));
                Config.members.serializeDatamatikerClassSimple();

                try {
                    embed = new EmbedBuilder()
                            .setColor(Color.decode("#0099ff"))
                            .setTitle("Added new user " + json.get("login").toString().replaceAll("\"", ""), "https://github.com/" + name)
                            .setThumbnail(json.get("avatar_url").toString().replaceAll("\"", ""))
                            .addField("Public repositories", "[" + json.get("public_repos").toString().replaceAll("\"", "") + " repositor" + (!json.get("public_repos").toString().replaceAll("\"", "").equals("1") ? "ies" : "y") + "](https://github.com/" + json.get("login").toString().replaceAll("\"", "") + "?tab=repositories)", true)
                            .addField("Followers", "[" + json.get("followers").toString().replaceAll("\"", "") + " follower" + (json.get("public_repos").toString().replaceAll("\"", "").equals("1") ? "s" : "") + "](https://github.com/" + json.get("login").toString().replaceAll("\"", "") + "?tab=followers)", true)
                            .addField("Following", "[Following " + json.get("following").toString().replaceAll("\"", "") + "](https://github.com/" + json.get("login").toString().replaceAll("\"", "") + "?tab=following)", true)
                            .setFooter("Account created " + json.get("created_at").toString().replaceAll("\"", ""));
                    Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
                    embed.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            case "*testmsg" -> EditMessage.testMessage();
            case "*testedit" -> EditMessage.editMessage();
        }

/*
        // EMBEDS
        final EmbedBuilder embed = new EmbedBuilder();
        embed.setTimestamp(Instant.now());
        embed.setColor(Color.RED);
        embed.setTitle("Message Logged");
        embed.addField("BotChannel","<#"+ channelID +">", false);
        embed.addField("Member", member, false);
        embed.addField("Member ID", memberID, false);
        embed.addField("Message", message, false);
        embed.addField("Message ID", messageID, false);
        embed.addField("Jump URL", jumpURL, false);

//        event.getJDA().getGuildById(Bot.bot.getServerID()).getTextChannelById(channelID).sendMessage(embed.build()).queue();
        Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
        embed.clear();
*/
    }
}
