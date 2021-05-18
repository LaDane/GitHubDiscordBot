package Commands;


import BotMessage.BotMsgChart;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

import Chart.*;
import BotLoop.*;
import Chart.MemberBarChart;
import Core.Config;
import Member.Member;
import Web.API;
import net.dv8tion.jda.api.entities.User;

public class CmdUserstats {
    public static void cmdUserstats(String msg, String memberID, String channelID) {
        if (!msg.contains(" ")) {                           // display stats for self
            Member member = Cmd.getMemberWithID(memberID);
            displayUserStats(member, channelID);
        } else if (msg.contains("<@!")) {                     // display stats for tagged discord user
            String stripped = msg.replaceAll("/userstats ", "");
            String discordID = stripped.substring(stripped.lastIndexOf("!") + 1).replaceAll(">", "");
            Consumer<? super User> callbackUser = (response) -> displayUserStats(Cmd.getMemberWithID(response.getId()), channelID);
            Config.jda.retrieveUserById(discordID).queue(callbackUser);
            return;
        } else if (msg.contains(" ")) {                       // display stats for github account
            String githubName = msg.replaceAll("/userstats ", "");
            for (Member member : Config.members.getMembers()) {
                if (githubName.equalsIgnoreCase(member.getMemberGithubName())) {
                    displayUserStats(member, channelID);
                }
            }
        }

    }

    private static void displayUserStats(Member member, String channelID) {
        if (member == null) {
            Cmd.sendErrorEmbed("Account does not exist!", null, channelID);
            return;
        }

        EmbedBuilder memberEmbed = member.memberEmbed(member.getMemberGithubName());
        Config.guild.getTextChannelById(channelID).sendMessage(memberEmbed.build()).queue();
        memberEmbed.clear();

        // Member doughnut chart
        String doughnutURL = DoughnutChart.doughnutChart(member);
        EmbedBuilder doughnutEmbed = BotMsgChart.chartEmbed(doughnutURL, member.getMemberColor());
        Config.guild.getTextChannelById(channelID).sendMessage(doughnutEmbed.build()).queue();
        doughnutEmbed.clear();

        // Member pie chart
        String pieURL = PieChart.pieChart(member);
        EmbedBuilder pieEmbed = BotMsgChart.chartEmbed(pieURL, member.getMemberColor());
        Config.guild.getTextChannelById(channelID).sendMessage(pieEmbed.build()).queue();
        pieEmbed.clear();

        // Member stacked bar chart
        String stackURL = StackedBarChart.stackedBarChart(member);
        EmbedBuilder stackEmbed = BotMsgChart.chartEmbed(stackURL, member.getMemberColor());
        Config.guild.getTextChannelById(channelID).sendMessage(stackEmbed.build()).queue();
        stackEmbed.clear();

        // Member line chart
        String lineURL = LineChartCommits.lineChartCommits(member);
        EmbedBuilder lineEmbed = BotMsgChart.chartEmbed(lineURL, member.getMemberColor());
        Config.guild.getTextChannelById(channelID).sendMessage(lineEmbed.build()).queue();
        lineEmbed.clear();
    }
}