package Commands;

import Core.Config;
import Member.Member;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

import java.awt.*;
import java.util.function.Consumer;

public class CmdRemove {
    private static String successColor = "#0099ff";
    private static String[] adminIDs = {"212238899613925376", "174162544712220672", "338099136006062082", "213289077095661569"};

    public static void cmdRemove(String msg, String memberID, String channelID) {
        if (!msg.contains(" "))                             // remove self
            removeMember(memberID, channelID);

        else if (msg.contains("<@!")) {                     // remove another account
            for (String adminID : adminIDs) {
                if (adminID.equals(memberID)) {
                    String discordID = msg.substring(msg.lastIndexOf("!") + 1).replaceAll(">", "");

                    Consumer<?super User> callbackUser = (response) -> removeMember(discordID, channelID);
                    Config.jda.retrieveUserById(discordID).queue(callbackUser);
                }
            }
        }
    }

    public static void removeMember(String memberID, String channelID) {
        Member memberCheck = Cmd.getMemberWithID(memberID);
        if (memberCheck == null)
            Cmd.sendErrorEmbed("Account not found!", null, channelID);
        else {
            // Confirm embed
            EmbedBuilder embed = new EmbedBuilder().setColor(Color.decode(successColor));
            embed.setTitle("Removed account "+ memberCheck.getMemberGithubName());
            embed.setThumbnail(memberCheck.getMemberGithubAvatarURL());
            Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
            embed.clear();

            TextChannel mChannel = Config.guild.getTextChannelById(Config.allChannels.getMembersChannel().getChannelID());
            if (mChannel == null) {System.out.println("ERROR: Members channel does not exist!"); return;}

            mChannel.retrieveMessageById(memberCheck.getMemberDiscordMsgID()).queue((message) -> {
                message.delete().queue();
            }, (failure) -> {
                if (failure instanceof ErrorResponseException) {
                    System.out.println("ERROR: Member embed message does not exist in Members channel!");
                }
            });
//            }

            // Save
            Config.members.removeFromMembers(memberCheck);
            Config.members.serializeMembersSimple();
        }
    }
}
