package Commands;

import Core.Config;
import Member.Member;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.function.Consumer;

public class CmdRemove {
    private static String successColor = "#0099ff";

    public static void cmdRemove(String msg, String memberID, String channelID) {
        if (!msg.contains(" "))                             // remove self
            removeMember(memberID, channelID);

        else if (msg.contains("<@!")) {                     // remove another account
            String discordID = msg.substring(msg.lastIndexOf("!") + 1).replaceAll(">", "");

            Consumer<?super User> callbackUser = (response) -> removeMember(discordID, channelID);
            Config.jda.retrieveUserById(discordID).queue(callbackUser);
        }
    }

    private static void removeMember(String memberID, String channelID) {
        Member memberCheck = Cmd.getMemberWithID(memberID);
        if (memberCheck == null)
            Cmd.sendErrorEmbed("Account not found!", null, channelID);
        else {
            EmbedBuilder embed = new EmbedBuilder().setColor(Color.decode(successColor));
            embed.setTitle("Removed account "+ memberCheck.getMemberGithubName());
            embed.setThumbnail(memberCheck.getMemberGithubAvatarURL());
            Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
            embed.clear();

            Config.members.removeFromMembers(memberCheck);
            Config.members.serializeMembersSimple();
        }
    }
}
