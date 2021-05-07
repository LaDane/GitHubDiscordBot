package Message;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.*;
import java.time.Instant;

import Bot.*;
import Core.*;


public class MessageListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(final GuildMessageReceivedEvent event) {

        String guildID = event.getGuild().getId();
        String channelID = event.getChannel().getId();
        String member = event.getMessage().getAuthor().getAsTag();
        String memberID = event.getAuthor().getId();
        String message = event.getMessage().getContentRaw();
        String messageID = event.getMessageId();
        String jumpURL = event.getMessage().getJumpUrl();

        // Don't continue if bot is message author
        if (memberID.equals(Bot.bot.getBotID()))
            return;




        // EMBEDS
        final EmbedBuilder embed = new EmbedBuilder();
        embed.setTimestamp(Instant.now());
        embed.setColor(Color.RED);
        embed.setTitle("Message Logged");
        embed.addField("BotChannel","<#"+ channelID +">", false);
        embed.addField("Member", member, false);
        embed.addField("Member ID", memberID, false);
        embed.addField("Message", message, false);
        embed.addField("Message ID", messageID, false);
        embed.addField("Jump URL", jumpURL, false);

//        event.getJDA().getGuildById(Bot.bot.getServerID()).getTextChannelById(channelID).sendMessage(embed.build()).queue();
        Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
        embed.clear();
    }
}
