package Commands;

import Chart.LineChartCommits;
import Chart.StackedBarChart;
import Core.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import Chart.*;
import java.awt.*;
import Web.*;

public class CmdHelp {
    public static void cmdHelp(String channelID) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.decode("#0099ff"))
                .setTitle("-----------------------------[ GitHub Stat Tracker ]-----------------------------")
                .addField("[required] {optional}", "\u200B", false)
                .addField("/add [name]", "Adds the following user to the tracking system and leaderboard", true)
                .addField("/remove", "Removes your account from the tracking system and leaderboard (resets points)", true)
                .addField("/recent {name} {days}", "Checks the users commit history within the specified amount of days (defaults to your stats and 14 days)", true)
                .addField("/userstats {name}", "Checks various statistics for the user, including their point score (defaults to your stats)", true)
                .addField("/leaderboard", "Shows a leaderboard with all the registered users and their point score", true)
                .addField("/flip [points]", "Feelin' risky? Put your points on the line in a game of luck", true);
        Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
        embed.clear();
    }
}
