package Bot;

import BotMessage.BotMsgChart;
import BotMessage.BotMsgLeaderboard;
import BotMessage.BotMsgShop;
import Chart.*;
import Core.Config;
import Message.EditMessage;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Objects;

public class SetupMessages {
    public static void setupMessages() {
        if (Config.allChannels.getLeaderboardChannel() == null || Config.allChannels.getGraphsChannel() == null) {
            System.out.println("ERROR: Channels have not been setup! Cannot create bot messages!");
            return;
        }
        String leaderChannelID = Config.allChannels.getLeaderboardChannel().getChannelID();
        String graphsChannelID = Config.allChannels.getGraphsChannel().getChannelID();

        // Shop
        EmbedBuilder shopEmbed = BotMsgShop.shopEmbed();
        Objects.requireNonNull(Config.guild.getTextChannelById(leaderChannelID)).sendMessage(shopEmbed.build()).queue((message) -> {
            Config.botMsg.setShopID(message.getId());
            EditMessage.editMessage(message.getId(), leaderChannelID);
            Config.botMsg.serializeBotMessageSimple();
        });
        shopEmbed.clear();

        // Leaderboard
        EmbedBuilder leaderboardEmbed = BotMsgLeaderboard.leaderboardEmbed();
        Objects.requireNonNull(Config.guild.getTextChannelById(leaderChannelID)).sendMessage(leaderboardEmbed.build()).queue((message) -> {
            Config.botMsg.setLeaderboardID(message.getId());
            Config.botMsg.serializeBotMessageSimple();
        });
        leaderboardEmbed.clear();

        // Gauge chart
        String gaugeURL = GaugeChart.gaugeChart();
        EmbedBuilder gaugeEmbed = BotMsgChart.chartEmbed(gaugeURL);
        Objects.requireNonNull(Config.guild.getTextChannelById(graphsChannelID)).sendMessage(gaugeEmbed.build()).queue((message) -> {
            Config.botMsg.setGaugeChartID(message.getId());
            Config.botMsg.serializeBotMessageSimple();
        });
        gaugeEmbed.clear();

        // Donut chart
        String doughnutURL = DoughnutChart.doughnutChart();
        EmbedBuilder doughnutEmbed = BotMsgChart.chartEmbed(doughnutURL);
        Objects.requireNonNull(Config.guild.getTextChannelById(graphsChannelID)).sendMessage(doughnutEmbed.build()).queue((message) -> {
            Config.botMsg.setDonutChartID(message.getId());
            Config.botMsg.serializeBotMessageSimple();
        });
        doughnutEmbed.clear();

        // Pie chart
        String pieURL = PieChart.pieChart();
        EmbedBuilder pieEmbed = BotMsgChart.chartEmbed(pieURL);
        Objects.requireNonNull(Config.guild.getTextChannelById(graphsChannelID)).sendMessage(pieEmbed.build()).queue((message) -> {
            Config.botMsg.setPieChartID(message.getId());
            Config.botMsg.serializeBotMessageSimple();
        });
        pieEmbed.clear();

        // Stacked bar chart
        String stackBarURL = StackedBarChart.stackedBarChart();
        EmbedBuilder stackBarEmbed = BotMsgChart.chartEmbed(stackBarURL);
        Objects.requireNonNull(Config.guild.getTextChannelById(graphsChannelID)).sendMessage(stackBarEmbed.build()).queue((message) -> {
            Config.botMsg.setStackBarChartID(message.getId());
            Config.botMsg.serializeBotMessageSimple();
        });
        stackBarEmbed.clear();

        // Line chart
        String lineURL = LineChartCommits.lineChartCommits();
        EmbedBuilder lineEmbed = BotMsgChart.chartEmbed(lineURL);
        Objects.requireNonNull(Config.guild.getTextChannelById(graphsChannelID)).sendMessage(lineEmbed.build()).queue((message) -> {
            Config.botMsg.setLineChartID(message.getId());
            Config.botMsg.serializeBotMessageSimple();
        });
        lineEmbed.clear();
    }
}
