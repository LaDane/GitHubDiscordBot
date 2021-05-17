package BotMessage;

import Core.Config;
import Member.Member;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BotMsgLeaderboard {
    public static EmbedBuilder leaderboardEmbed() {
//        ArrayList<String[]> users = new ArrayList<>();
//        for (Member d : Config.members.getMembers()) {
//            String tmp[] = {
//                    d.getMemberGithubName(),
//                    String.valueOf(d.getMemberPoints())
//            };
//            users.add(tmp);
//        }
//        Collections.sort(users, new Comparator<String[]>() {
//            public int compare(String[] strings, String[] otherStrings) {
//                return strings[1].compareTo(otherStrings[1]);
//            }
//        });
//        Collections.reverse(users);

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
        return embed;
    }
}
