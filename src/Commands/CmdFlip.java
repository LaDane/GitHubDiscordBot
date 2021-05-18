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
            member.setMemberPoints(member.getMemberPoints() - points);
            member.updateMemberLogs(0,0,0,0, 0,points,0);

            embed.setColor(Color.red).setTitle("Better luck next time!")
                    .addField("You lost " + points + " point" + (points != 1 ? "s" : ""), "You now have " + member.getMemberPoints() + " point" + (points != 1 ? "s" : ""), false);
            Config.botLogs.updateBotLogs(0,0,0,0,0,points,0,0,0);
        } else {
            member.setMemberPoints(member.getMemberPoints() + points);
            member.updateMemberLogs(0,0,0,0, points,0,0);

            embed.setColor(Color.green).setTitle("Congratulations!")
                    .addField("You won " + points + " point" + (points != 1 ? "s" : ""), "You now have " + member.getMemberPoints() + " point" + (points != 1 ? "s" : ""), false);
            Config.botLogs.updateBotLogs(0,0,0,0,points,0,0,0,0);
        }

        member.editMemberEmbed();

        Config.members.serializeMembersSimple();
        Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
        embed.clear();
    }
}
