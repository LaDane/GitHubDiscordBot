package BotMessage;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class BotMsgChart {
    public static EmbedBuilder chartEmbed(String imageURL) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.decode("#0099ff"))
                .setImage(imageURL);
        return embed;
    }

    public static EmbedBuilder chartEmbed(String imageURL, String color) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.decode(color))
                .setImage(imageURL);
        return embed;
    }
}
