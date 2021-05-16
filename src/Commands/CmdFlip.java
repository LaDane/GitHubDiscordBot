package Commands;

import Core.Config;
import Member.Member;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class CmdFlip {
    public static void cmdFlip(String msg, String memberID, String channelID) {
        EmbedBuilder embed = new EmbedBuilder();
        Member member = Cmd.getMemberWithID(memberID);
        if (member == null) {Cmd.sendErrorEmbed("User not found", null, channelID); return;}

        int points = Integer.parseInt(msg.split(" ")[1]);

        if (points < 1) {Cmd.sendErrorEmbed("Invalid number", null, channelID); return;}
        if (member.getMemberPoints() < points) {Cmd.sendErrorEmbed("Not enough points", null, channelID); return;}

        if (Math.random() < 0.5) {
            embed.setColor(Color.red).setTitle("Better luck next time!").addField("You lost " + points + " point" + (points != 1 ? "s" : ""), "\u200B", false);
            member.setMemberPoints(member.getMemberPoints() - points);
            Config.botLogs.updateBotLogs(0,0,0,0,0,points,1,1);
        } else {
            embed.setColor(Color.green).setTitle("Congratulations!").addField("You won " + points + " point" + (points != 1 ? "s" : ""), "\u200B", false);
            member.setMemberPoints(member.getMemberPoints() + points);
            Config.botLogs.updateBotLogs(0,0,0,0,points,0,1,1);
        }
        Config.members.serializeMembersSimple();
        Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
        embed.clear();
    }
}
