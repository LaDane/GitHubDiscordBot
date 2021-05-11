package Message;

import Core.Config;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.requests.ErrorResponse;

import java.nio.channels.Channel;

public class EditMessage {

    private static long testMessageID;
    private static String botTestChannelID = "841210986705846302";

    public static void testMessage() {
        Config.guild.getTextChannelById(botTestChannelID).sendMessage("Edit this message").queue((message -> {
            testMessageID = message.getIdLong();
        }));
    }

    public static void editMessage() {

        TextChannel channel = Config.guild.getTextChannelById(botTestChannelID);

        channel.retrieveMessageById(testMessageID).queue((message) -> {
            // use the message here, its an async callback
//            message.addReaction(reaction).queue();                  // add reaction to a message
            message.editMessage("bleh").queue();
            System.out.println("Message Content: " + message.getContentDisplay());
        }, (failure) -> {
            // if the retrieve request failed this will be called (also async)
            if (failure instanceof ErrorResponseException) {
                ErrorResponseException ex = (ErrorResponseException) failure;
                if (ex.getErrorResponse() == ErrorResponse.UNKNOWN_MESSAGE) {
                    // this means the message doesn't exist
                    channel.sendMessage("That message doesn't exist !").queue();
                }
            }
            failure.printStackTrace();
        });
    }
}
