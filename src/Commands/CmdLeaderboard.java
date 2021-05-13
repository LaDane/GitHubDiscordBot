package Commands;

import Core.Config;
import Member.Member;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CmdLeaderboard {
    public static void cmdLeaderboard(String channelID) {
        EmbedBuilder embed = new EmbedBuilder()
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
