package Bot;

import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import java.util.function.Consumer;

import Core.Config;

public class SetupChannels {

    private static final String categoryName = "STAT TRACKER";
    private enum channelType {VOICE, TEXT}
    private static final String[][] channelNames = {
            {"members", "TEXT"},
            {"leaderboard-shop", "TEXT"},
            {"commits-commands", "TEXT"},
            {"graphs", "TEXT"}
    };

    private static Category cat;

    public static void setupChannels() {
        newCategory(categoryName);
    }

    private static void newCategory(String name) {
        Consumer<? super Category> callbackCategory = (response) -> saveCategory(response);
        Config.guild.createCategory(name).setPosition(0).queue(callbackCategory);
    }

    private static void saveCategory(Category response) {
        cat = response;
        createAllChannels();
    }

    private static void createAllChannels() {
        for (String[] channelName : channelNames) {
            if (channelName[1].equals("VOICE"))
                createNewChannel(channelName[0], channelType.VOICE);
            else if (channelName[1].equals("TEXT"))
                createNewChannel(channelName[0], channelType.TEXT);
        }
    }

    private static void createNewChannel(String name, channelType type) {
        Consumer<? super VoiceChannel> callbackVoice = (response) -> saveChannel(response.getId(), name);
        Consumer<? super TextChannel> callbackText = (response) -> saveChannel(response.getId(), name);

        if (type.equals(channelType.VOICE))
            Config.guild.createVoiceChannel(name, cat).queue(callbackVoice);
        else if (type.equals(channelType.TEXT))
            Config.guild.createTextChannel(name, cat).queue(callbackText);
    }

    private static void saveChannel(String id, String name) {
        switch(name) {
            case ("members") -> Config.allChannels.setMembersChannel(id, name);
            case ("leaderboard-shop") -> Config.allChannels.setLeaderboardChannel(id, name);
            case ("commits-commands") -> Config.allChannels.setCommitsCommandsChannel(id, name);
            case ("graphs") -> Config.allChannels.setGraphsChannel(id, name);
        }

        Config.allChannels.serializeAllChannelsSimple();
    }
}
