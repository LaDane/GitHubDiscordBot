package BotChannel;

import Core.Config;
import net.dv8tion.jda.api.entities.TextChannel;

// We never ended up using the ability to edit channel names
public class EditChannelName {
    public static void editChannelName(BotChannel channel, String newName) {

        TextChannel textChannel = Config.guild.getTextChannelById(channel.getChannelID());
        textChannel.getManager().setName(newName).queue();
        channel.setChannelName(newName);
    }
}
