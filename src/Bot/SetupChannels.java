package Bot;

import BotChannel.BotChannel;
import Core.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import net.dv8tion.jda.api.requests.restaction.order.CategoryOrderAction;
import net.dv8tion.jda.api.requests.restaction.order.ChannelOrderAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.channels.Channel;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class SetupChannels {

    private static String categoryName = "STATS";
    private static String channelNames[] = {
            "Most Commits = ",
            "Most Repos = ",
            "Most points = "
    };


    public static void setupChannels() {

//        newCategory(categoryName);

        for (int i = 0; i < channelNames.length; i++) {
            newTextChannel(channelNames[i]);
        }
    }

    private static void newCategory(String name) {
        Config.guild.createCategory(name).queue();
        Category cat = Config.guild.getCategoriesByName(name, true).get(0);
        Config.guild.modifyCategoryPositions().selectPosition(cat.getPosition()).moveTo(0).queue();

//        ChannelAction ca = Config.guild.createCategory(name);
//        CategoryOrderAction coAction = new CategoryOrderAction(Config.guild, ChannelType.CATEGORY);
//        ca.modifyCategoryPositions()
    }

    private static void newTextChannel(String name) {
        String[] channelID = new String[1];
//        Config.guild.createTextChannel(name).queue(textChannel -> {
//            channelID[0] = textChannel.getId();});
        Config.guild.createVoiceChannel(name).queue(voiceChannel -> {
            channelID[0] = voiceChannel.getId();});

//        Channel channel = Config.guild.getChannelsByName(name, true).get(0)

        BotChannel channel = new BotChannel(channelID[0], name);
        Config.allChannels.addToAllChannels(channel);
        Config.allChannels.serializeAllChannelsSimple();
    }
}
