package Message;

import Member.Member;
import net.dv8tion.jda.api.entities.TextChannel;
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

        // Delete message if posted in channels that don't allow member messages
        if (channelID.equals(Config.allChannels.getMembersChannel().getChannelID()) ||
                channelID.equals(Config.allChannels.getLeaderboardChannel().getChannelID()) ||
                channelID.equals(Config.allChannels.getGraphsChannel().getChannelID()) ) {
            TextChannel tChannel = Config.guild.getTextChannelById(channelID);
            if (tChannel != null) {
                tChannel.retrieveMessageById(messageID).queue((msg) -> {
                    msg.delete().queue();
                }, (failure) -> {
                    if (failure instanceof ErrorResponseException) {
                        System.out.println("ERROR: Could not delete a message from a member sent in "+ tChannel.getName() +" channel!");
                    }
                });
            }
        }

        // Commands
        if (channelID.equals(Config.allChannels.getCommitsCommandsChannel().getChannelID())) {
            String cmd = message.split(" ")[0].toLowerCase();
            switch (cmd) {
                case ("/help") -> CmdHelp.cmdHelp(channelID);
                case ("/add") -> CmdAdd.cmdAdd(message, memberID, channelID);
                case ("/remove") -> CmdRemove.cmdRemove(message, memberID, channelID);
                case ("/recent") -> CmdRecent.cmdRecent(message, memberID, channelID);
                case ("/userstats") -> CmdUserstats.cmdUserstats(message, memberID, channelID);
                case ("/flip") -> CmdFlip.cmdFlip(message, memberID, channelID);
            }
        }
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if (event.getMember().getUser().equals(event.getJDA().getSelfUser())) {
            return;
        }

        String leaderBoardChannelID = Config.allChannels.getLeaderboardChannel().getChannelID();

        if (event.getChannel().getId().equals(leaderBoardChannelID)) {
            event.getReaction().removeReaction(event.getUser()).queue();

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


