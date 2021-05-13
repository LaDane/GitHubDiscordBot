package Commands;

import Core.Config;
import Member.Member;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.Instant;

public class Cmd {

    public static Member getMemberWithID(String memberID) {
        for (Member member : Config.members.getMembers()) {
            if (member.getMemberDiscordID().equals(memberID))
                return member;
        }
        return null;
    }

    public static void sendErrorEmbed(String title, String thumbnail, String channelID) {
        EmbedBuilder embed = new EmbedBuilder().setColor(Color.RED).setTitle(title);
        if (thumbnail != null)
            embed.setThumbnail(thumbnail);
        Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
        embed.clear();
    }
}
