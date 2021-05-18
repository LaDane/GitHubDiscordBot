package BotMessage;

import Core.Config;
import Member.Member;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class BotMsgLeaderboard {
    public static EmbedBuilder leaderboardEmbed() {

        ArrayList<Member> memberArray = Config.members.getMembers();
        memberArray.sort(new LeaderboardSorter());
        Collections.reverse(memberArray);

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.decode("#0099ff"))
                .setTitle("Leaderboard");
        int i = 1;
        for (Member d : memberArray) {
            embed.addField("#" + i, "[" + d.getMemberGithubName() + "](https://github.com/" + d.getMemberGithubName() + "): " + d.getMemberPoints() + " point" + (d.getMemberPoints() != 1 ? "s" : ""), false);
            i++;
        }
        if (memberArray.size() == 0)
            embed.addField("No members to display!", "\u200B", true);
        return embed;
    }
}
