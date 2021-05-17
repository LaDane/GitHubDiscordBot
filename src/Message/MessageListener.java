package Message;

import BotChannel.BotChannel;
import Member.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import BotMessage.*;
import Bot.*;
import Core.*;
import Commands.*;

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
        if (memberID.equals(Bot.bot.getBotID())) {
            Config.botLogs.updateBotLogs(0,0,0,0,0,0,0,1,1);
            return;
        }
        Config.botLogs.updateBotLogs(0,0,0,0,0,0,0,1,0);

        // Check if messages are sent in correct channels, if not then delete the message
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

        if (channelID.equals(membersChannel.getChannelID()) ||
                channelID.equals(leaderboardChannel.getChannelID()) ||
                channelID.equals(graphsChannel.getChannelID()) ) {
            TextChannel tChannel = Config.guild.getTextChannelById(channelID);
            if (tChannel != null) {
                tChannel.retrieveMessageById(messageID).queue((msg) -> {
                    msg.delete().queue();
                }, (failure) -> {
                    if (failure instanceof ErrorResponseException) {
                        System.out.println("ERROR: Could not delete a message from a member sent in the wrong channel!");
                    }
                });
            }
        }

        // Handle error if commits commands channel doesnt exist
        if (commitsCommandsChannel == null || !channelID.equals(commitsCommandsChannel.getChannelID()))
            return;

        // TODO: THESE DESERIALIZE FUNCTIONS WILL FUCK UP MEMBER BOT LOOP SINCE CONFIG.MEMBERS WILL BE OVERWRITTEN IN MEMORY!
//        Config.members.deserializeMembersSimple();
//        Config.allChannels.deserializeAllChannelsSimple();

        //Switch
        String cmd = message.split(" ")[0].toLowerCase();
        switch (cmd) {
            case ("/help") -> CmdHelp.cmdHelp(channelID);
            case ("/add") -> CmdAdd.cmdAdd(message, memberID, channelID);
            case ("/remove") -> CmdRemove.cmdRemove(message, memberID, channelID);
            case ("/recent") -> CmdRecent.cmdRecent(message, memberID, channelID);
            case ("/leaderboard") -> CmdLeaderboard.cmdLeaderboard(channelID);
            case ("/userstats") -> CmdUserstats.cmdUserstats(message, memberID, channelID);
            case ("/flip") -> CmdFlip.cmdFlip(message, memberID, channelID);
            case ("/shop") -> CmdShop.cmdShop(memberID, channelID);
        }
        /*
        if (message.contains("/help")) CmdHelp.cmdHelp(channelID);
        if (message.contains("/add")) CmdAdd.cmdAdd(message, memberID, channelID);
        if (message.contains("/remove")) CmdRemove.cmdRemove(message, memberID, channelID);
        if (message.contains("/recent")) CmdRecent.cmdRecent(message, memberID, channelID);
        */
    }
    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if (event.getMember().getUser().equals(event.getJDA().getSelfUser())) {
            return;
        }

        event.getReaction().removeReaction(event.getUser()).queue();

        BotChannel leaderBoardChannel = null;
        for (BotChannel channel : Config.allChannels.getAllChannels()) {
            if (channel.getChannelName().equals("leaderboard"))
                leaderBoardChannel = channel;

        }
        String leaderBoardChannelID = leaderBoardChannel.getChannelID();
        if (event.getChannel().getId().equals(leaderBoardChannelID)) {
            String memberID = event.getMember().getUser().getId();
            String reactionEmojiName = event.getReactionEmote().getName();
            Member reactionMember = null;
            for (Member member : Config.members.getMembers()) {
                if (member.getMemberDiscordID().equals(memberID)){
                    reactionMember = member;
                }
            }

            switch (reactionEmojiName) {
                case ("\uD83D\uDFEB") -> BotMsgShop.buyProduct(reactionMember,"#8B4513",100); //Brown
                case ("\uD83D\uDFE8") -> BotMsgShop.buyProduct(reactionMember,"#F4AF37",1000); //Yellow
                case ("\uD83D\uDFE7") -> BotMsgShop.buyProduct(reactionMember,"#d19600",2500); //Orange
                case ("\uD83D\uDFE6") -> BotMsgShop.buyProduct(reactionMember,"#000a69",5000); //Blue
                case ("\uD83D\uDFE9") -> BotMsgShop.buyProduct(reactionMember,"#097501",7500); //Green
                case ("\uD83D\uDFE5") -> BotMsgShop.buyProduct(reactionMember,"#B80F0A",12500); //Red
                case ("\uD83D\uDFEA") -> BotMsgShop.buyProduct(reactionMember,"#9966CC",25000); //Purple
                case ("⬜") -> BotMsgShop.buyProduct(reactionMember,"#787878",50000); //White
                case ("⬛") -> BotMsgShop.buyProduct(reactionMember,"#303136",1000000); //Black (discord embed color)
            }
        }
    }

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


        /*
        String cmd = message.split(" ")[0].toLowerCase();

        EmbedBuilder embed = new EmbedBuilder();


        switch (cmd) {
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

