package Message;

import Web.Request;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.*;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;

import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.time.Instant;
import java.io.Reader;
import java.util.*;
import java.net.URL;

import java.text.SimpleDateFormat;


import Member.*;
import Bot.*;
import Core.*;


public class MessageListener extends ListenerAdapter {


    //temp; moving later
    void checkRecent(String memberName, int days, String channelID) {
        JsonObject json = Request.request("https://api.github.com/search/commits?q=author:" + memberName + "&sort=author-date&order=desc&page=1", "application/vnd.github.cloak-preview");
        System.out.println(json.get("items").getAsJsonArray().size());
        ArrayList<String[]> items = new ArrayList<>();



        for (int i = 0; i < json.get("items").getAsJsonArray().size(); i++) {
            long now = Instant.now().toEpochMilli();
            long date = Instant.parse(json.get("items").getAsJsonArray().get(i).getAsJsonObject().get("commit").getAsJsonObject().get("committer").getAsJsonObject().get("date").toString().replaceAll("\"", "")).toEpochMilli();
            if(now - date < (long)days * 24 * 60 * 60 * 1000){
                String tmp[] = {
                        json.get("items").getAsJsonArray().get(i).getAsJsonObject().get("repository").getAsJsonObject().get("full_name").toString().replaceAll("\"", ""),
                        json.get("items").getAsJsonArray().get(i).getAsJsonObject().get("commit").getAsJsonObject().get("message").toString().replaceAll("\"", ""),
                        json.get("items").getAsJsonArray().get(i).getAsJsonObject().get("commit").getAsJsonObject().get("committer").getAsJsonObject().get("date").toString().replaceAll("\"", "")
                };
                items.add(tmp);
            }
        }

        JsonObject json2 = Request.request("https://api.github.com/users/" + memberName, null);

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.decode("#0099ff"))
                .setTitle("Commits by " + memberName + " within the last " + days + " day" + (days != 1 ? "s" : ""))
                .setThumbnail(json2.get("avatar_url").toString().replaceAll("\"", ""));

        for (String[] d : items) {
            embed.addField("\u200B", "**Repo**: " + d[0] + "\n**Message**: " + d[1] + "\n**Time**: " + d[2], false);
        }

        Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
        embed.clear();


        //System.out.println(items.get(0)[0]);

        /*for (String[] d : items) {
            System.out.println(Arrays.toString(d));
            System.out.println(d[1]);
        }*/
        return;
    }

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
                        .setColor(Color.decode("#0099ff"))
                        .setTitle("-----------------------------[ GitHub Stat Tracker ]-----------------------------")
                        .addField("[required] {optional}", "\u200B", false)
                        .addField("*adduser [name]", "Adds the following user to the tracking system and leaderboard", true)
                        .addField("*removeuser", "Removes your account from the tracking system and leaderboard (resets points)", true)
                        .addField("*recent {name} {days}", "Checks the users commit history within the specified amount of days (defaults to your stats and 14 days)", true)
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
                        }
                        ;
                        Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
                        embed.clear();
                        return;
                    }
                }

                JsonObject json = Request.request("https://api.github.com/users/" + name, null);
                JsonObject json2 = Request.request("https://api.github.com/search/commits?q=author:" + name + "&sort=author-date&order=desc&page=1", "application/vnd.github.cloak-preview");

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
                            .addField("Followers", "[" + json.get("followers").toString().replaceAll("\"", "") + " follower" + (json.get("public_repos").toString().replaceAll("\"", "").equals("1") ? "" : "s") + "](https://github.com/" + json.get("login").toString().replaceAll("\"", "") + "?tab=followers)", true)
                            .addField("Following", "[Following " + json.get("following").toString().replaceAll("\"", "") + "](https://github.com/" + json.get("login").toString().replaceAll("\"", "") + "?tab=following)", true)
                            .setFooter("Account created " + json.get("created_at").toString().replaceAll("\"", ""));
                    Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
                    embed.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            case "*removeuser" -> {
                int i = 0;
                for (Member d : Config.members.getMembers()) {
                    if (d.getMemberDiscordID().equals(memberID)) {
                        embed = new EmbedBuilder()
                                .setColor(Color.decode("#0099ff"))
                                .setTitle("Removed user " + d.getMemberGithubName())
                                .setThumbnail(d.getMemberGithubAvatarURL());
                        Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
                        embed.clear();
                        Config.members.removeFromMembers(i);
                        Config.members.serializeDatamatikerClassSimple();
                        return;
                    }
                    i++;
                }
                embed = new EmbedBuilder()
                        .setColor(Color.red)
                        .setTitle("User not found");
                Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
                embed.clear();
            }

            case "*recent" -> {
                boolean hasAccount = false;
                Member selectedMember = null;
                for (Member d : Config.members.getMembers()) {
                    if (d.getMemberDiscordID().equals(memberID)) {
                        hasAccount = true;
                        selectedMember = d;
                    }
                }

                //User does not have an account
                if (!hasAccount) {

                    //Message has 1st param
                    try {
                        if (!message.split(" ")[1].equals("")) {
                            try {
                                //Message has 2nd param (int)
                                if (message.split(" ")[2].chars().allMatch(Character::isDigit)) {
                                    checkRecent(message.split(" ")[1], Integer.parseInt(message.split(" ")[2]), channelID);
                                //Message has invalid 2nd param
                                } else {
                                    checkRecent(message.split(" ")[1], 14, channelID);
                                }
                            //Message has no 2nd param
                            } catch (Exception e) {
                                checkRecent(message.split(" ")[1], 14, channelID);
                            }
                        }
                    //Message has no params (error)
                    } catch (Exception e) {
                        embed = new EmbedBuilder()
                                .setColor(Color.red)
                                .setTitle("User not found (or missing parameter)");
                        Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
                        embed.clear();
                        return;
                    }

                //User has an account
                } else {
                    //Message has 1st param
                    try {
                        if (!message.split(" ")[1].equals("")) {
                            try {
                                //Message has 2nd param (int)
                                if (message.split(" ")[2].chars().allMatch(Character::isDigit)) {
                                    checkRecent(message.split(" ")[1], Integer.parseInt(message.split(" ")[2]), channelID);
                                //Message has invalid 2nd param
                                } else {
                                    checkRecent(message.split(" ")[1], 14, channelID);
                                }
                            //Message has no 2nd param
                            } catch (Exception e) {
                                //1st param is int
                                if (message.split(" ")[1].chars().allMatch(Character::isDigit)) {
                                    checkRecent(selectedMember.getMemberGithubName(), Integer.parseInt(message.split(" ")[1]), channelID);
                                //1st param is string
                                } else {
                                    checkRecent(message.split(" ")[1], 14, channelID);
                                }
                            }
                        }

                    //Message has no params
                    } catch (Exception e) {
                        checkRecent(selectedMember.getMemberGithubName(), 14, channelID);
                    }
                }
            }

            case "*userstats" -> {
                String name = "";
                String userPoints = "0 (Not registered)";
                try {
                    //Message has param
                    if (!message.split(" ")[1].equals("")) {
                        name = message.split(" ")[1];

                        //Check if user with github name is in database
                        for (Member d : Config.members.getMembers()) {
                            if (d.getMemberGithubName().equalsIgnoreCase(name)) {
                                userPoints = String.valueOf(d.getMemberPoints());
                            }
                        }
                    }
                } catch (Exception e) {
                    //Message has no param

                    //Check if message author has an account
                    for (Member d : Config.members.getMembers()) {
                        if (d.getMemberDiscordID().equals(memberID)) {
                            name = d.getMemberGithubName();
                            userPoints = String.valueOf(d.getMemberPoints());
                        }
                    }
                }

                if (name.equals("")) {
                    embed = new EmbedBuilder()
                            .setColor(Color.red)
                            .setTitle("User not found (or missing parameter)");
                    Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
                    embed.clear();
                    return;
                }

                try {
                    JsonObject json = Request.request("https://api.github.com/users/" + name, null);
                    embed = new EmbedBuilder()
                            .setColor(Color.decode("#0099ff"))
                            .setTitle("User stats for " + json.get("login").toString().replaceAll("\"", ""))
                            .setThumbnail(json.get("avatar_url").toString().replaceAll("\"", ""))
                            .addField("Public repositories", "[" + json.get("public_repos").toString().replaceAll("\"", "") + " repositor" + (json.get("public_repos").toString().replaceAll("\"", "").equals("1") ? "y" : "ies") + "](https://github.com/" + json.get("login").toString().replaceAll("\"", "") + "?tab=repositories)", true)
                            .addField("Followers", "[" + json.get("followers").toString().replaceAll("\"", "") + " follower" + (json.get("followers").toString().replaceAll("\"", "").equals("1") ? "" : "s") + "](https://github.com/" + json.get("login").toString().replaceAll("\"", "") + "?tab=followers)", true)
                            .addField("Following", "[Following " + json.get("following").toString().replaceAll("\"", "") + "](https://github.com/" + json.get("login").toString().replaceAll("\"", "") + "?tab=following)", true)
                            .addField("Account created", json.get("created_at").toString().replaceAll("\"", ""), true)
                            .addField("Points", userPoints, true);
                    Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
                    embed.clear();
                } catch (Exception e) {
                    embed = new EmbedBuilder()
                            .setColor(Color.red)
                            .setTitle("GitHub user not found");
                    Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
                    embed.clear();
                }
            }

            case "*leaderboard" -> {
                embed = new EmbedBuilder()
                        .setColor(Color.decode("#0099ff"))
                        .setTitle("Leaderboard");

                ArrayList<String[]> users = new ArrayList<>();



                for (Member d : Config.members.getMembers()) {
                    String tmp[] = {
                        d.getMemberGithubName(),
                        String.valueOf(d.getMemberPoints())
                    };
                    users.add(tmp);
                }

                Collections.sort(users, new Comparator<String[]>() {
                    public int compare(String[] strings, String[] otherStrings) {
                        return strings[1].compareTo(otherStrings[1]);
                    }
                });

                Collections.reverse(users);
                int i = 1;
                for (String[] d : users) {
                    embed.addField("#" + i, "[" + d[0] + "](https://github.com/" + d[0] + "): " + d[1] + " point" + (!d[1].equals("1") ? "s" : ""), false);
                    i++;
                }
                Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
                embed.clear();
            }
        }
    }
}
