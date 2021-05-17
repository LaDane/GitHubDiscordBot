package BotLoop;

import BotChannel.BotChannel;
import BotMessage.BotMsgChart;
import BotMessage.BotMsgLeaderboard;
import Chart.*;
import Core.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

public class LoopMessages {
    public static void loopMessages(BotChannel leaderboardChannel, BotChannel graphsChannel) {
        // Config.botMsg.deserializeBotLogsSimple();

        if (leaderboardChannel == null || graphsChannel == null) {
            System.out.println("ERROR: Channels have not been setup! Cannot edit bot messages!");
            return;
        }

        TextChannel lChannel = Config.guild.getTextChannelById(leaderboardChannel.getChannelID());
        TextChannel gChannel = Config.guild.getTextChannelById(graphsChannel.getChannelID());
        if (lChannel == null || gChannel == null) {
            System.out.println("ERROR: Leaderboard / Graphs channel does not exist!");
            return;
        }

        // Leaderboard
        EmbedBuilder leaderboardEmbed = BotMsgLeaderboard.leaderboardEmbed();
        lChannel.retrieveMessageById(Config.botMsg.getLeaderboardID()).queue((message) -> {
            message.editMessage(leaderboardEmbed.build()).queue();
        }, (failure) -> {
            if (failure instanceof ErrorResponseException) {
                System.out.println("ERROR: Failed to update leaderboard message!");
            }
        });

        // Gauge chart
        String gaugeURL = GaugeChart.gaugeChart();
        EmbedBuilder gaugeEmbed = BotMsgChart.chartEmbed(gaugeURL);
        gChannel.retrieveMessageById(Config.botMsg.getGaugeChartID()).queue((message) -> {
            message.editMessage(gaugeEmbed.build()).queue();
        }, (failure) -> {
            if (failure instanceof ErrorResponseException) {
                System.out.println("ERROR: Failed to update gauge chart message!");
            }
        });

        // Doughnut chart
        String doughnutURL = DoughnutChart.doughnutChart();
        EmbedBuilder doughnutEmbed = BotMsgChart.chartEmbed(doughnutURL);
        gChannel.retrieveMessageById(Config.botMsg.getDonutChartID()).queue((message) -> {
            message.editMessage(doughnutEmbed.build()).queue();
        }, (failure) -> {
            if (failure instanceof ErrorResponseException) {
                System.out.println("ERROR: Failed to update doughnut chart message!");
            }
        });

        // Pie chart
        String pieURL = PieChart.pieChart();
        EmbedBuilder pieEmbed = BotMsgChart.chartEmbed(pieURL);
        gChannel.retrieveMessageById(Config.botMsg.getPieChartID()).queue((message) -> {
            message.editMessage(pieEmbed.build()).queue();
        }, (failure) -> {
            if (failure instanceof ErrorResponseException) {
                System.out.println("ERROR: Failed to update pie chart message!");
            }
        });

        // Stacked bar chart
        String stackBarURL = StackedBarChart.stackedBarChart();
        EmbedBuilder stackBarEmbed = BotMsgChart.chartEmbed(stackBarURL);
        gChannel.retrieveMessageById(Config.botMsg.getStackBarChartID()).queue((message) -> {
            message.editMessage(stackBarEmbed.build()).queue();
        }, (failure) -> {
            if (failure instanceof ErrorResponseException) {
                System.out.println("ERROR: Failed to update stacked bar chart message!");
            }
        });

        // Line chart
        String lineURL = LineChartCommits.lineChartCommits();
        EmbedBuilder lineEmbed = BotMsgChart.chartEmbed(lineURL);
        gChannel.retrieveMessageById(Config.botMsg.getLineChartID()).queue((message) -> {
            message.editMessage(lineEmbed.build()).queue();
        }, (failure) -> {
            if (failure instanceof ErrorResponseException) {
                System.out.println("ERROR: Failed to update line chart message!");
            }
        });

        System.out.println("Updated bot messages");
    }
}
