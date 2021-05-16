package Bot;

import BotChannel.BotChannel;
import BotMessage.BotMsgChart;
import BotMessage.BotMsgLeaderboard;
import BotMessage.BotMsgShop;
import Chart.*;
import Core.Config;
import net.dv8tion.jda.api.EmbedBuilder;

public class SetupMessages {
    public static void setupMessages() {
        BotChannel leaderboardChannel = null;
        BotChannel graphsChannel = null;
        for (BotChannel channel : Config.allChannels.getAllChannels()) {
            if (channel.getChannelName().equals("leaderboard")) leaderboardChannel = channel;
            if (channel.getChannelName().equals("graphs")) graphsChannel = channel;
        }

        // Error handling
        if (leaderboardChannel == null || graphsChannel == null){
            System.out.println("Channels have not been setup! Cannot create bot messages!");
            return;
        }

        // Shop
        EmbedBuilder shopEmbed = BotMsgShop.shopEmbed();
        Config.guild.getTextChannelById(leaderboardChannel.getChannelID()).sendMessage(shopEmbed.build()).queue((message) -> {
            String shopMessageID = message.getId();
            Config.botMsg.setShopID(shopMessageID);
        });
        shopEmbed.clear();

        // Leaderboard
        EmbedBuilder leaderboardEmbed = BotMsgLeaderboard.leaderboardEmbed();
        Config.guild.getTextChannelById(leaderboardChannel.getChannelID()).sendMessage(leaderboardEmbed.build()).queue((message) -> {
            String leaderboardID = message.getId();
            Config.botMsg.setLeaderboardID(leaderboardID);
        });
        leaderboardEmbed.clear();

        // Gauge chart
        String gaugeURL = GaugeChart.gaugeChart();
        EmbedBuilder gaugeEmbed = BotMsgChart.chartEmbed(gaugeURL);
        Config.guild.getTextChannelById(graphsChannel.getChannelID()).sendMessage(gaugeEmbed.build()).queue((message) -> {
            String gaugeID = message.getId();
            Config.botMsg.setGaugeChartID(gaugeID);
        });

        // Donut chart
        String doughnutURL = DoughnutChart.doughnutChart();
        EmbedBuilder doughnutEmbed = BotMsgChart.chartEmbed(doughnutURL);
        Config.guild.getTextChannelById(graphsChannel.getChannelID()).sendMessage(doughnutEmbed.build()).queue((message) -> {
            String doughnutID = message.getId();
            Config.botMsg.setDonutChartID(doughnutID);
        });

        // Pie chart
        String pieURL = PieChart.pieChart();
        EmbedBuilder pieEmbed = BotMsgChart.chartEmbed(pieURL);
        Config.guild.getTextChannelById(graphsChannel.getChannelID()).sendMessage(pieEmbed.build()).queue((message) -> {
            String pieID = message.getId();
            Config.botMsg.setPieChartID(pieID);
        });

        // Stacked bar chart
        String stackBarURL = StackedBarChart.stackedBarChart();
        EmbedBuilder stackBarEmbed = BotMsgChart.chartEmbed(stackBarURL);
        Config.guild.getTextChannelById(graphsChannel.getChannelID()).sendMessage(stackBarEmbed.build()).queue((message) -> {
            String stackBarID = message.getId();
            Config.botMsg.setStackBarChartID(stackBarID);
        });

        // Line chart
        String lineURL = LineChartCommits.lineChartCommits();
        EmbedBuilder lineEmbed = BotMsgChart.chartEmbed(lineURL);
        Config.guild.getTextChannelById(graphsChannel.getChannelID()).sendMessage(lineEmbed.build()).queue((message) -> {
            String lineID = message.getId();
            Config.botMsg.setLineChartID(lineID);
        });

        // Save to json
        Config.botMsg.serializeBotMessageSimple();
    }
}
